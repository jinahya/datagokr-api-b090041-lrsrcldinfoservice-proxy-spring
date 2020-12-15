module datagokr.api.b090041_.lrsrcldinfoservice.proxy.app.main {
    exports com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.domain;
    exports com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.repository;
    requires java.base;
    requires java.xml;
    requires java.xml.bind;
    requires java.persistence;
    requires java.validation;
    requires lombok;
    requires org.aspectj.weaver;
    requires reactor.core;
    requires spring.beans;
    requires spring.context;
    requires spring.core;
    requires spring.web;
    requires spring.webflux;
    requires spring.data.jpa;
}