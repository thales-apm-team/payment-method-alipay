package com.payline.payment.alipay.bean.object;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.payline.payment.alipay.exception.InvalidDataException;

import java.io.IOException;

public class Response {
    private final static XmlMapper xmlMapper = new XmlMapper();
    private Trade trade;

    public Trade getTrade() {
        return trade;
    }

    public void setTrade(Trade trade) {
        this.trade = trade;
    }

    public static Response fromXml(String xml) {
        try {
            return xmlMapper.readValue(xml, Response.class);
        } catch (IOException e) {
            throw new InvalidDataException("Unable to parse XML CheckStatusResponse", e);
        }
    }
}