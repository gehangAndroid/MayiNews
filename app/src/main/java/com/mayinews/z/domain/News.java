package com.mayinews.z.domain;

import java.util.List;

/**
 * Created by GAI on 2018/1/9 0009.
 *
 */

public class News {


    /**
     *  count : 12
     *  result : [{"category_id":"26","cover_id":"1022006,1022007,1022008","from":"全网资讯","id":"333978","images":["http://static.mayinews.com/Uploads/Picture/2018-01-09/5a54156bccc3c.jpeg?x-oss-process=style/small_115_85","http://static.mayinews.com/Uploads/Picture/2018-01-09/5a54156bf10a0.jpeg?x-oss-process=style/small_115_85","http://static.mayinews.com/Uploads/Picture/2018-01-09/5a54156c3ee7e.jpeg?x-oss-process=style/small_115_85"],"title":"CES2018 Ujet电动车源自欧洲的时尚之选","type":"3"},{"category_id":"11","cover_id":"1021861","from":"中国新闻网","id":"333920","images":["http://static.mayinews.com/Uploads/Picture/2018-01-09/5a541534c05da.jpeg?x-oss-process=style/small_115_85"],"title":"四川光雾山雪后银装素裹 美不胜收","type":"2"},{"category_id":"17","cover_id":"1022145,1022146,1022147","from":"网易体育","id":"334053","images":["http://static.mayinews.com/Uploads/Picture/2018-01-09/5a5415d955481.jpeg?x-oss-process=style/small_115_85","http://static.mayinews.com/Uploads/Picture/2018-01-09/5a5415d97b762.jpeg?x-oss-process=style/small_115_85","http://static.mayinews.com/Uploads/Picture/2018-01-09/5a5415d9b0935.jpeg?x-oss-process=style/small_115_85"],"title":"波多老师家乡开店卖肉千人捧场 狂热者吹5小时冷风","type":"3"},{"category_id":"13","cover_id":"1022299","from":"新浪娱乐","id":"334123","images":["http://static.mayinews.com/Uploads/Picture/2018-01-09/5a54161342f74.jpeg?x-oss-process=style/small_115_85"],"title":"妮可封后归功母亲 麦克格雷格感谢绯闻女友引争议","type":"2"},{"category_id":"13","cover_id":"1022309","from":"大众网","id":"334130","images":["http://static.mayinews.com/Uploads/Picture/2018-01-09/5a54161665f2f.jpeg?x-oss-process=style/small_115_85"],"title":"《无法拥抱的你》打破偶像剧\u201c套路\u201d","type":"2"},{"category_id":"11","cover_id":"1021830,1021831,1021832","from":"中国新闻网","id":"333913","images":["http://static.mayinews.com/Uploads/Picture/2018-01-09/5a54152c3318a.jpeg?x-oss-process=style/small_115_85","http://static.mayinews.com/Uploads/Picture/2018-01-09/5a54152c652aa.jpeg?x-oss-process=style/small_115_85","http://static.mayinews.com/Uploads/Picture/2018-01-09/5a54152c880ac.jpeg?x-oss-process=style/small_115_85"],"title":"中国\u201c下猛药\u201d治污，2018年将交出怎样的答卷？","type":"3"},{"category_id":"20","cover_id":"1022378","from":"新浪科技","id":"334168","images":["http://static.mayinews.com/Uploads/Picture/2018-01-09/5a54162da6650.png?x-oss-process=style/small_115_85"],"title":"法国检察官着手调查苹果\u201c降频门\u201d事件","type":"2"},{"category_id":"17","cover_id":"1022134","from":"网易体育","id":"334046","images":["http://static.mayinews.com/Uploads/Picture/2018-01-09/5a5415d5738bf.jpeg?x-oss-process=style/small_115_85"],"title":"北控3场比赛移师五棵松 将与北京首钢共用主场","type":"2"},{"category_id":"24","cover_id":"1022661,1022662,1022663","from":"全网资讯","id":"334259","images":["http://static.mayinews.com/Uploads/Picture/2018-01-09/5a54167934cde.jpeg?x-oss-process=style/small_115_85","http://static.mayinews.com/Uploads/Picture/2018-01-09/5a54167969873.jpeg?x-oss-process=style/small_115_85","http://static.mayinews.com/Uploads/Picture/2018-01-09/5a54167990af7.jpeg?x-oss-process=style/small_115_85"],"title":"46-55岁为生命高危期，做好这五大要，防病健康到..","type":"3"},{"category_id":"26","cover_id":"1022015,1022016,1022017","from":"搜狐汽车","id":"333980","images":["http://static.mayinews.com/Uploads/Picture/2018-01-09/5a54156e98da4.jpeg?x-oss-process=style/small_115_85","http://static.mayinews.com/Uploads/Picture/2018-01-09/5a54156f2ae34.jpeg?x-oss-process=style/small_115_85","http://static.mayinews.com/Uploads/Picture/2018-01-09/5a54156f78b03.jpeg?x-oss-process=style/small_115_85"],"title":"领克01首月销量出炉！月销6012辆啪啪打了谁的脸？就问VV7你怕了没","type":"3"},{"category_id":"24","cover_id":"1022565","from":"全网资讯","id":"334250","images":["http://static.mayinews.com/Uploads/Picture/2018-01-09/5a5416664c051.jpeg?x-oss-process=style/small_115_85"],"title":"4个所有人都适用的增肌方法，你也能有好身材","type":"2"},{"category_id":"20","cover_id":"1022332","from":"新浪","id":"334140","images":["http://static.mayinews.com/Uploads/Picture/2018-01-09/5a54161f48f1e.jpeg?x-oss-process=style/small_115_85"],"title":"AMD第二代Ryzen四月见 Vega转攻机器学习和集显","type":"2"}]
     *  stauts : 1
     */

    private int count;
    private int stauts;
    private List<ResultBean> result;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStauts() {
        return stauts;
    }

    public void setStauts(int stauts) {
        this.stauts = stauts;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * category_id : 26
         * cover_id : 1022006,1022007,1022008
         * from : 全网资讯
         * id : 333978
         * images : ["http://static.mayinews.com/Uploads/Picture/2018-01-09/5a54156bccc3c.jpeg?x-oss-process=style/small_115_85","http://static.mayinews.com/Uploads/Picture/2018-01-09/5a54156bf10a0.jpeg?x-oss-process=style/small_115_85","http://static.mayinews.com/Uploads/Picture/2018-01-09/5a54156c3ee7e.jpeg?x-oss-process=style/small_115_85"]
         * title : CES2018 Ujet电动车源自欧洲的时尚之选
         * type : 3
         */

        private String category_id;
        private String cover_id;
        private String from;
        private String id;
        private String title;
        private String type;
        private List<String> images;

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getCover_id() {
            return cover_id;
        }

        public void setCover_id(String cover_id) {
            this.cover_id = cover_id;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
