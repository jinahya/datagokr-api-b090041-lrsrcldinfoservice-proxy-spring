package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.stereotype.remote;

import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.domain.LunCalInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Setter
@Getter
@Slf4j
public class Response {

    @XmlAccessorType(XmlAccessType.FIELD)
    @Setter
    @Getter
    @Slf4j
    public static class Header {

        public static final String RESULT_CODE_SUCCESS = "00";

        public boolean isResultCodeSuccess() {
            return RESULT_CODE_SUCCESS.equals(resultCode);
        }

        private String resultCode;

        private String resultMsg;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @Slf4j
    public static class Body {

        @XmlAccessorType(XmlAccessType.FIELD)
        @Setter
        @Getter
        @Slf4j
        public static class Item {

            // -----------------------------------------------------------------------------------------------------------------
            public LunCalInfo toLunCalInfo() {
                final LunCalInfo lunCalInfo = new LunCalInfo();
                lunCalInfo.setSolarDate(getSolarDate());
                lunCalInfo.setLunarDate(getLunarDate());
                lunCalInfo.setSecha(getLunSecha());
                lunCalInfo.setWolgeon(getLunWolgeon());
                lunCalInfo.setIljin(getLunIljin());
                return lunCalInfo;
            }

            @Override
            public String toString() {
                return super.toString() + '{'
                       + "lunYear=" + lunYear
                       + ",lunMonth=" + lunMonth
                       + ",lunDay=" + lunDay
                       + ",solYear=" + solYear
                       + ",solMonth=" + solMonth
                       + ",solDay=" + solDay
                       + ",solLeapyear=" + solLeapyear
                       + ",lunLeapmonth=" + lunLeapmonth
                       + ",solWeek=" + solWeek
                       + ",lunSecha=" + lunSecha
                       + ",lunWolgeon=" + lunWolgeon
                       + ",lunIljin=" + lunIljin
                       + ",lunNday=" + lunNday
                       + ",solJd=" + solJd
                       + '}';
            }

            // -----------------------------------------------------------------------------------------------------------------
            public LocalDate getLunarDate() {
                return LocalDate.of(Integer.parseInt(lunYear), Integer.parseInt(lunMonth), Integer.parseInt(lunDay));
            }

            public LocalDate getSolarDate() {
                return LocalDate.of(Integer.parseInt(solYear), Integer.parseInt(solMonth), Integer.parseInt(solDay));
            }

            public boolean isLeapMonth() {
                return "윤".equals(lunLeapmonth);
            }

            // -----------------------------------------------------------------------------------------------------------------
            @XmlElement
            private String lunYear;

            @XmlElement
            private String lunMonth;

            @XmlElement
            private String lunDay;

            @XmlElement
            private String solYear;

            @XmlElement
            private String solMonth;

            @XmlElement
            private String solDay;

            @XmlElement
            private String solLeapyear;

            @XmlElement
            private String lunLeapmonth;

            @XmlElement
            private String solWeek;

            @XmlElement
            private String lunSecha;

            @XmlElement(required = false)
            private String lunWolgeon;

            @XmlElement
            private String lunIljin;

            @XmlElement
            private int lunNday;

            @XmlElement
            private int solJd;
        }

        // -------------------------------------------------------------------------------------------------------------
        @Override
        public String toString() {
            return super.toString() + '{'
                   + "items=" + items
                   + "}";
        }

        // ------------------------------------------------------------------------------------------------------- items
        public List<Item> getItems() {
            if (items == null) {
                items = new ArrayList<>();
            }
            return items;
        }

        // -------------------------------------------------------------------------------------------------------------
        @XmlElementWrapper
        @XmlElement(name = "item")
        private List<@Valid @NotNull Item> items;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    public String toString() {
        return super.toString() + '{'
               + "header=" + header
               + ",body=" + body
               + '}';
    }

    // -----------------------------------------------------------------------------------------------------------------
    @NotNull
    @XmlElement(required = true)
    private Header header;

    @NotNull
    @XmlElement(required = true)
    private Body body;
}
