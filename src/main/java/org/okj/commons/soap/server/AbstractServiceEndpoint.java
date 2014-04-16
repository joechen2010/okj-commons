package org.okj.commons.soap.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Date;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.codec.binary.Base64;
import org.okj.commons.reflection.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.oxm.XmlMappingException;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;


public class AbstractServiceEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractServiceEndpoint.class);
    private Jaxb2Marshaller soapSimulatorMarshaller;
    
    
    protected byte[] obtainByteData(InputStream inputStream, boolean isBase64) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // Read bytes from the input stream in bytes.length-sized chunks and
        // write
        // them into the output stream
        for (int readBytes = inputStream.read(); readBytes >= 0; readBytes = inputStream.read())
            outputStream.write(readBytes);

        // Convert the contents of the output stream into a byte array
        byte[] byteData = outputStream.toByteArray();
        
        // Close the streams
        inputStream.close();
        outputStream.close();
        if(isBase64){
            return Base64.encodeBase64(byteData);
        }
        return byteData;
    }
    
    protected boolean between(Date start, Date end, Date value) {
        if (value == null) {
            return true;
        }
        if (start != null && value.before(start)) {
            return false;
        }
        if (end != null && value.after(end)) {
            return false;
        }
        return true;
    }

    protected <T> JAXBElement<T> unmarshal(Resource resource, Class<T> declaredType) {
        if (resource != null) {
            try {
                @SuppressWarnings("unchecked")
                JAXBElement<T> result = (JAXBElement<T>) soapSimulatorMarshaller.unmarshal(new StreamSource(resource
                        .getInputStream()));
                return result;
            } catch (XmlMappingException e) {
                LOGGER.error("", e);
            } catch (IOException e) {
                LOGGER.error("", e);
            }
        }
        return new JAXBElement<T>(getQName(declaredType), declaredType, null,
                ReflectionUtil.createInstance(declaredType));
    }
    
    protected <T> T unmarshalObject(Resource resource, Class<T> declaredType) {
        if (resource != null) {
            try {
                @SuppressWarnings("unchecked")
                T result = (T) soapSimulatorMarshaller.unmarshal(new StreamSource(resource
                        .getInputStream()));
                return result;
            } catch (XmlMappingException e) {
                LOGGER.error("", e);
            } catch (IOException e) {
                LOGGER.error("", e);
            }
        }
        return ReflectionUtil.createInstance(declaredType);
    }

    protected <T> QName getQName(Class<T> declaredType) {
        XmlType type = declaredType.getAnnotation(XmlType.class);
        return new QName(getNamespace(), type.name());
    }

    protected String getNamespace() {
        for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
            if (getClass().getName().equals(stackTraceElement.getClassName())) {
                String methodName = stackTraceElement.getMethodName();
                for (Method m : getClass().getMethods()) {
                    if (methodName.equals(m.getName())) {
                        PayloadRoot payloadRoot = m.getAnnotation(PayloadRoot.class);
                        if (payloadRoot != null) {
                            return payloadRoot.namespace();
                        }
                    }
                }
            }
        }
        return null;
    }

    public void setSoapSimulatorMarshaller(Jaxb2Marshaller soapSimulatorMarshaller) {
        this.soapSimulatorMarshaller = soapSimulatorMarshaller;
    }

    
    
}
