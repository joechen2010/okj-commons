package org.okj.commons.hyperic;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.okj.commons.hyperic.type.Agent;
import org.okj.commons.hyperic.type.AgentsResponse;
import org.okj.commons.hyperic.type.MetricData;
import org.okj.commons.hyperic.type.MetricTemplate;
import org.okj.commons.hyperic.type.MetricTemplatesResponse;
import org.okj.commons.hyperic.type.MetricsDataResponse;
import org.okj.commons.hyperic.type.Resource;
import org.okj.commons.hyperic.type.ResourcesResponse;
import org.okj.commons.hyperic.type.ResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import sun.misc.BASE64Encoder;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class HypericApiClient {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private String hypericServerAddress = "147.128.104.127:7080";

    private String hypericAuthentication = "hqadmin:hqadmin";

    public void setHypericServerAddress(String hypericServerAddress) {
        this.hypericServerAddress = hypericServerAddress;
    }

    public void setHypericAuthentication(String hypericAuthentication) {
        this.hypericAuthentication = hypericAuthentication;
    }

    private final RestTemplate restTemplate = new RestTemplate();

    public List<Agent> getAgents() {

        ResponseEntity<AgentsResponse> res = exchange("/hqu/hqapi1/agent/list.hqu", AgentsResponse.class);
        if (res != null && res.getBody().getStatus().equals(ResponseStatus.SUCCESS)) {
            return res.getBody().getAgent();
        }
        return new ArrayList<Agent>();
    }

    private List<MetricData> getMetrics(int agentId, int duration, MetricType metricType) throws Exception {
        // get resource
        ResponseEntity<ResourcesResponse> res = exchange(
                "/hqu/hqapi1/resource/find.hqu?verbose=false&children=true&agentId=" + agentId, ResourcesResponse.class);
        List<Resource> freeMemory = Lists.newArrayList();
        filterResourceByPrototype(res.getBody().getResource(), freeMemory, metricType.getPrototypeName());

        // get metric template id
        ResponseEntity<MetricTemplatesResponse> mtRes = exchange("/hqu/hqapi1/metric/getTemplates.hqu?prototype="
                + metricType.getPrototypeName(), MetricTemplatesResponse.class);

        int templateId = getTemplateIdByAlias(mtRes.getBody().getMetricTemplate(), metricType.getAlias());
        List<String> ids = Lists.newArrayList();
        for (Resource r : freeMemory) {
            ids.add(Integer.toString(r.getId()));
        }

        // get metric data
        Map<String, String[]> params = Maps.newHashMap();
        params.put("ids", ids.toArray(new String[ids.size()]));
        params.put("templateId", new String[] { Integer.toString(templateId) });
        long end = System.currentTimeMillis();
        params.put("start", new String[] { Long.toString(end - duration) });
        params.put("end", new String[] { Long.toString(end) });
        ResponseEntity<MetricsDataResponse> metricRes = exchange(
                buildUri("/hqu/hqapi1/metric/getResourceData.hqu", params), MetricsDataResponse.class);
        return metricRes.getBody().getMetricData();
    }

    public List<MetricData> getDiskMetrics(int agentId, int duration) {
        try {
            return getMetrics(agentId, duration, MetricType.DISK);
        } catch (Exception e) {
            log.error("Failed query disk metrics " + MetricType.DISK, e);
        }
        return Collections.emptyList();
    }

    public List<MetricData> getMemoryMetrics(int agentId, int duration) {
        try {
            return getMetrics(agentId, duration, MetricType.MEMORY);
        } catch (Exception e) {
            log.error("Failed query free memory metrics " + MetricType.MEMORY, e);
        }
        return Collections.emptyList();
    }

    public List<MetricData> getCPUMetrics(int agentId, int duration) {
        try {
            return getMetrics(agentId, duration, MetricType.CPU);
        } catch (Exception e) {
            log.error("Failed query cpu metrics " + MetricType.CPU, e);
        }
        return Collections.emptyList();
    }

    private String buildUri(String path, Map<String, String[]> params) throws IOException {
        StringBuffer uri = new StringBuffer(path);
        if (uri.charAt(uri.length() - 1) != '?') {
            uri.append("?");
        }

        boolean append = false;

        for (Map.Entry<String, String[]> e : params.entrySet()) {
            for (String val : e.getValue()) {
                if (val != null) {
                    if (append) {
                        uri.append("&");
                    }
                    uri.append(e.getKey()).append("=").append(URLEncoder.encode(val, "UTF-8"));
                    append = true;
                }
            }
        }
        return uri.toString();
    }

    private int getTemplateIdByAlias(List<MetricTemplate> templates, String alias) {
        for (MetricTemplate mt : templates) {
            if (mt.getAlias().equals(alias)) {
                return mt.getId();
            }
        }
        log.warn("No matched metric template found for alias '" + alias + "'");
        return -1;
    }

    private void filterResourceByPrototype(List<Resource> resources, List<Resource> filtered, String prototypeName) {
        for (Resource r : resources) {
            if (r.getResourcePrototype().getName().equals(prototypeName)) {
                filtered.add(r);
            } else if (r.getResource() != null && !r.getResource().isEmpty()) {
                filterResourceByPrototype(r.getResource(), filtered, prototypeName);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private <T> ResponseEntity<T> exchange(String uri, Class<T> responseBody, Object... args) {
        HttpHeaders authHeader = new HttpHeaders();
        authHeader.add("Authorization", "Basic " + new BASE64Encoder().encode(hypericAuthentication.getBytes()));
        @SuppressWarnings("rawtypes")
        HttpEntity emptyEntity = new HttpEntity(authHeader);
        log.debug("HQ hyperic api involking url: " + hypericServerAddress + uri);
        try {
            return restTemplate.exchange("http://" + hypericServerAddress + uri, HttpMethod.GET, emptyEntity,
                    responseBody, args);
        } catch (RestClientException e) {
            log.error("Exception when get HQ status!", e);
        }
        return null;
    }

    // public static void main(String[] args) {
    // System.out.println(Base64.encodeBase64String("hqadmin:111111".getBytes()));
    // System.out.println(new
    // BASE64Encoder().encode("hqadmin:111111".getBytes()));
    // }
    // public static void main(String args[]) throws Exception {
    // System.out.println(FetchURL("http://147.128.104.127:7080/hqu/hqapi1/agent/list.hqu",
    // "hqadmin", "111111"));
    // }
    //
    // public static String FetchURL(String urlString, String uid, String pw)
    // throws java.io.IOException,
    // java.net.MalformedURLException {
    // String outStr = new String();
    // URL url = new URL(urlString);
    //
    // String User = uid + ":" + pw;
    // BASE64Encoder encoder = new BASE64Encoder();
    // String encoding = new String(encoder.encode(User.getBytes()));
    // URLConnection uc = url.openConnection();
    // uc.setRequestProperty("Authorization", "Basic " +
    // encoder.encode(User.getBytes()));
    //
    // InputStream content = (InputStream) uc.getContent();
    // BufferedReader in = new BufferedReader(new InputStreamReader(content));
    // String line;
    // while ((line = in.readLine()) != null) {
    // outStr += line;
    // }
    //
    // return outStr;
    // }
}
