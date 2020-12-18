module com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.spring {
    requires com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.spring;
    requires io.netty.handler;
    requires io.netty.transport;
    requires java.xml;
    requires java.xml.bind;
    requires java.persistence;
    requires java.validation;
    requires lombok;
    requires org.aspectj.weaver;
    requires reactor.core;
    requires reactor.netty.core;
    requires reactor.netty.http;
    requires spring.beans;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.core;
    requires spring.data.jpa;
    requires spring.tx;
    requires spring.web;
    requires spring.webflux;
    exports com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy;
    exports com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.domain;
    exports com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.repository;
    exports com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.stereotype;
}