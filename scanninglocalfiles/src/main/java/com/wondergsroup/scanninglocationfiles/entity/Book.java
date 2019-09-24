package com.wondergsroup.scanninglocationfiles.entity;

import java.util.List;

public class Book {

    /**
     * sites : {"site":[{"id":"1","name":"菜鸟教程","url":"www.runoob.com"},{"id":"2","name":"菜鸟工具","url":"c.runoob.com"},{"id":"3","name":"Google","url":"www.google.com"}]}
     */

    private SitesBean sites;

    public SitesBean getSites() {
        return sites;
    }

    public void setSites(SitesBean sites) {
        this.sites = sites;
    }

    public static class SitesBean {
        private List<SiteBean> site;

        public List<SiteBean> getSite() {
            return site;
        }

        public void setSite(List<SiteBean> site) {
            this.site = site;
        }

        public static class SiteBean {
            /**
             * id : 1
             * name : 菜鸟教程
             * url : www.runoob.com
             */

            private String id;
            private String name;
            private String url;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
