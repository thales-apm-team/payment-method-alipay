package com.payline.payment.alipay.bean.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.payline.payment.alipay.bean.object.Response;
import com.payline.payment.alipay.exception.InvalidDataException;

import java.io.IOException;


@JsonIgnoreProperties({ "request" })
@JacksonXmlRootElement(namespace = "alipay", localName = "AlipayAPIResponse")
public class AlipayAPIResponse {

    private String is_success;
    private Response response;
    private String error;
    private String sign;
    private String sign_type;
    private static XmlMapper xmlMapper = new XmlMapper();

    public String getIs_success() {
        return is_success;
    }

    public boolean isSuccess()
    {
        return getIs_success().equalsIgnoreCase("t");
    }

    public void setIs_success(String is_success) {
        this.is_success = is_success;
    }

    public Response getResponse() {
        return response;
    }

    public String getError()
    {
        return error;
    }

    public void setError(String error)
    {
        this.error = error;
    }

    public static AlipayAPIResponse fromXml(String xml) {
        try {
            return xmlMapper.readValue(xml, AlipayAPIResponse.class);
        } catch (IOException e) {
            throw new InvalidDataException("Unable to parse XML AlipayAPIResponse", e);
        }
    }
}
