package com.mayinews.z.domain;

import java.util.List;

/**
 * Created by Administrator on 2018/1/12 0012.
 */

public class NewsChannel {


    /**
     * status : 1
     * count : 6
     * result : [{"id":"13","name":"yule","title":"娱乐","sort":"1"},{"id":"11","name":"shehui","title":"社会","sort":"2"},{"id":"17","name":"tiyu","title":"体育","sort":"3"},{"id":"20","name":"keji","title":"科技","sort":"5"},{"id":"26","name":"qiche","title":"汽车","sort":"8"},{"id":"24","name":"jiankang","title":"养生","sort":"9"}]
     */

    private int status;
    private int count;
    private List<ResultBean> result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 13
         * name : yule
         * title : 娱乐
         * sort : 1
         */

        private String id;
        private String name;
        private String title;
        private String sort;

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }
    }
}
