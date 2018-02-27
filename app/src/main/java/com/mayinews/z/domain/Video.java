package com.mayinews.z.domain;

import java.util.List;

/**
 * Created by Administrator on 2018/1/16 0016.
 */

public class Video {


    /**
     * count : 26
     * stauts : 1
     * result : [{"cover":"http://static.mayinews.com/Uploads/Picture/2017-11-06/5a003046f241b.jpeg","size":"19666510","desc":"","duration":"821","update_time":"1509961804","title":"开心麻花沈腾马丽小品《投其所好》","view":"0","vid":"fdad46a691e24d76b9061991c0c48628","create_time":"1509961319","comment":"0","cid":"130","uid":"18","status":"1","nickname":"我们一起看综艺"},{"duration":"186","update_time":"1509956038","title":"INFINITE 李成烈 台北见面会","size":"21473167","view":"5","vid":"830ec96f1ce64ae297e8b493380cae99","create_time":"1509955481","comment":"0","cid":"130","uid":"21","cover":"http://static.mayinews.com/Uploads/Picture/2017-11-06/5a0019c14a176.jpeg","desc":"","status":"1","nickname":"我的爱豆"},{"cover":"http://static.mayinews.com/Uploads/Picture/2017-11-06/5a0019a60d228.jpeg","size":"6422588","desc":"","duration":"29","update_time":"1509956010","title":"白敬亭说唱","view":"0","vid":"f5ab3ee3d372484b98b75eb68168170a","create_time":"1509955481","comment":"0","cid":"130","uid":"21","status":"1","nickname":"我的爱豆"},{"cover":"http://static.mayinews.com/Uploads/Picture/2017-11-06/5a0019b0d5dff.jpeg","size":"15206002","desc":"","duration":"139","update_time":"1509956023","title":"TWICE金浦机场出境","view":"0","vid":"e0b27aee2cbe4e379c2b1cfdb9e9f070","create_time":"1509955481","comment":"0","cid":"130","uid":"21","status":"1","nickname":"我的爱豆"},{"duration":"224","update_time":"1509503716","title":"一次看个大满足 性感天使许允美火辣舞蹈合集","size":"6924124","view":"2","vid":"234251d61bad4f0c97de582415f77644","create_time":"1509503241","comment":"0","cid":"116","uid":"17","cover":"http://static.mayinews.com/Uploads/Picture/2017-11-01/59f932d289329.jpeg","desc":"","status":"1","nickname":"舞林萌主"},{"cover":"http://static.mayinews.com/Uploads/Picture/2017-10-31/59f80b36470a6.jpeg","size":"7066701","desc":"","duration":"154","update_time":"1509428027","title":"AfreecaTV，舞蹈精选李秀彬系列10月29","view":"0","vid":"c33ac35eee404d1b921fc81f86173d22","create_time":"1509423379","comment":"0","cid":"116","uid":"17","status":"1","nickname":"舞林萌主"},{"cover":"http://static.mayinews.com/Uploads/Picture/2017-11-09/5a03b837dd1f6.jpeg","size":"5681160","desc":"","duration":"91","update_time":"1510193229","title":"《猎场》林拜第一次登门拜访，就遭到了郑秋冬这个耿直boy","view":"0","vid":"e4dde593f95241bcba658cf948a33f18","create_time":"1510193027","comment":"0","cid":"106","uid":"27","status":"1","nickname":"小仙女爱追剧"},{"duration":"202","update_time":"1509958584","title":"电视剧将军在上，惜音跳舞惜音表妹为叶昭跳舞","size":"34249888","view":"3","vid":"3654e4469d3848a9b10ead347b8ba8f8","create_time":"1509957549","comment":"0","cid":"106","uid":"27","cover":"http://static.mayinews.com/Uploads/Picture/2017-11-06/5a0023ab5ce76.jpeg","desc":"","status":"1","nickname":"小仙女爱追剧"},{"cover":"http://static.mayinews.com/Uploads/Picture/2017-10-24/59ef0d642a596.jpeg","size":"19800471","desc":"","duration":"193","update_time":"1508838762","title":"【玩吃鸡在游戏里开出租拉客】","view":"0","vid":"c59d45765800475c877e9548b9b1a945","create_time":"1508838556","comment":"0","cid":"110","uid":"15","status":"1","nickname":"长歌是大腿"},{"cover":"http://static.mayinews.com/Uploads/Picture/2017-10-23/59edb13d00573.jpeg","size":"59698391","desc":"","duration":"645","update_time":"1508749636","title":"【#不坑是怎样炼成的# \u2014\u2014娜可露露快速入门】","view":"0","vid":"b1c0418057114b339ae7ff34e13b45fb","create_time":"1508748461","comment":"0","cid":"110","uid":"15","status":"1","nickname":"长歌是大腿"},{"cover":"http://static.mayinews.com/Uploads/Picture/2017-11-06/5a0025bd7f6a0.jpeg","size":"335987","desc":"","duration":"11","update_time":"1509959144","title":"千万别去招惹喵星人，否则.....","view":"0","vid":"fcc6a983832a4066bcd44877a33b7e2b","create_time":"1509956614","comment":"0","cid":"89","uid":"16","status":"1","nickname":"铲屎达人"},{"cover":"http://static.mayinews.com/Uploads/Picture/2017-11-06/5a002546c5079.jpeg","size":"5356634","desc":"","duration":"33","update_time":"1509959091","title":"一对双胞胎宝宝抢夺一只奶嘴，场面有些失控，萌一屏血 \u200b","view":"0","vid":"d8c133ff24994cd3807a1fb8fe80295f","create_time":"1509956614","comment":"0","cid":"89","uid":"16","status":"1","nickname":"铲屎达人"},{"cover":"http://static.mayinews.com/Uploads/Picture/2017-11-06/5a0025bd7f6a0.jpeg","size":"335987","desc":"","duration":"11","update_time":"1509959144","title":"千万别去招惹喵星人，否则.....","view":"0","vid":"fcc6a983832a4066bcd44877a33b7e2b","create_time":"1509956614","comment":"0","cid":"89","uid":"16","status":"1","nickname":"铲屎达人"},{"cover":"http://static.mayinews.com/Uploads/Picture/2017-11-06/5a002546c5079.jpeg","size":"5356634","desc":"","duration":"33","update_time":"1509959091","title":"一对双胞胎宝宝抢夺一只奶嘴，场面有些失控，萌一屏血 \u200b","view":"0","vid":"d8c133ff24994cd3807a1fb8fe80295f","create_time":"1509956614","comment":"0","cid":"89","uid":"16","status":"1","nickname":"铲屎达人"},{"cover":"http://static.mayinews.com/Uploads/Picture/2017-11-03/59fc14ea5201d.jpeg","size":"876740","desc":"","duration":"17","update_time":"1509692653","title":"这舞蹈很不错","view":"0","vid":"3c18aa860cc5442099d8bb71a5c44eff","create_time":"1509691995","comment":"0","cid":"116","uid":"17","status":"1","nickname":"舞林萌主"},{"duration":"203","update_time":"0","title":"梁朝伟电影混剪","size":"21777936","view":"1","vid":"eff0f3034ff04369b8f59e2a0e4c5d1f","create_time":"1509525008","comment":"0","cid":"130","uid":"21","cover":"http://static.mayinews.com/Uploads/Picture/2017-11-01/59f98897b3a86.jpeg","desc":"","status":"2","nickname":"我的爱豆"},{"cover":"http://static.mayinews.com/Uploads/Picture/2017-10-27/59f301a2c480c.jpeg","size":"17529715","desc":"","duration":"169","update_time":"1509097900","title":"什么是引力波？给大家推荐一集《蓝猫淘气三千问》","view":"0","vid":"43a6c93a734c4701bf1c4633c3cd405d","create_time":"1509097541","comment":"0","cid":"129","uid":"24","status":"1","nickname":"相信科学"},{"duration":"244","update_time":"1503901230","title":"哈哈哈哈，笑死我了，小黄人比赛谁是第一","size":"15880497","view":"3","vid":"f6df6957e623427097124ac293843105","create_time":"1503900883","comment":"0","cid":"101","uid":"14","cover":"http://static.mayinews.com/Uploads/Picture/2017-08-28/59a3b622a1e87.jpeg","desc":"","status":"1","nickname":"爆笑小母猪"},{"cover":"http://static.mayinews.com/Uploads/Picture/2017-11-03/59fc3ce9963b7.jpeg","size":"6012391","desc":"","duration":"125","update_time":"1509702894","title":"喜欢吃珍珠奶茶的，不会自制珍珠丸子怎么行！","view":"0","vid":"44d513da6a1e46c6816b9ee5f7cb5867","create_time":"1509702707","comment":"0","cid":"108","uid":"13","status":"1","nickname":"深夜食堂"},{"cover":"http://static.mayinews.com/Uploads/Picture/2017-11-03/59fc3c82e6a10.jpeg","size":"8871319","desc":"","duration":"87","update_time":"1509702795","title":"巧克力曲奇棒,比以往酥松的曲奇，香香脆脆的曲奇更让我喜欢","view":"0","vid":"cf001474681c4451a35863bf5dc927f2","create_time":"1509702701","comment":"0","cid":"108","uid":"13","status":"1","nickname":"深夜食堂"},{"duration":"122","update_time":"1509952226","title":"放个毒！去韩国一定不能错过的餐厅：","size":"12500427","view":"1","vid":"a2ceddb2738842ee9905d6d020de6c1b","create_time":"1509951724","comment":"0","cid":"108","uid":"13","cover":"http://static.mayinews.com/Uploads/Picture/2017-11-06/5a000ad95c253.jpeg","desc":"","status":"1","nickname":"深夜食堂"},{"cover":"http://static.mayinews.com/Uploads/Picture/2017-11-03/59fc3ce9963b7.jpeg","size":"6012391","desc":"","duration":"125","update_time":"1509702894","title":"喜欢吃珍珠奶茶的，不会自制珍珠丸子怎么行！","view":"0","vid":"44d513da6a1e46c6816b9ee5f7cb5867","create_time":"1509702707","comment":"0","cid":"108","uid":"13","status":"1","nickname":"深夜食堂"},{"cover":"http://static.mayinews.com/Uploads/Picture/2017-11-06/5a003046f241b.jpeg","size":"19666510","desc":"","duration":"821","update_time":"1509961804","title":"开心麻花沈腾马丽小品《投其所好》","view":"0","vid":"fdad46a691e24d76b9061991c0c48628","create_time":"1509961319","comment":"0","cid":"130","uid":"18","status":"1","nickname":"我们一起看综艺"},{"cover":"http://static.mayinews.com/Uploads/Picture/2017-11-06/5a0030542a923.jpeg","size":"32290791","desc":"","duration":"237","update_time":"1509961846","title":"看黄子韬真正男子汉中的表现","view":"0","vid":"f8e7d88c5cd341ad805e6562e65e6be0","create_time":"1509961319","comment":"0","cid":"130","uid":"18","status":"1","nickname":"我们一起看综艺"},{"cover":"http://static.mayinews.com/Uploads/Picture/2017-11-06/5a000699b0fce.jpeg","size":"422514","desc":"","duration":"20","update_time":"1509951134","title":"「单手键盘」iOS11这个实用小功能你们知道吗？ 更新了","view":"0","vid":"fb783f329ecf499ea4035953f8a30ca7","create_time":"1509950520","comment":"0","cid":"129","uid":"24","status":"1","nickname":"相信科学"},{"cover":"http://static.mayinews.com/Uploads/Picture/2017-11-06/5a000712dd016.jpeg","size":"11035253","desc":"","duration":"103","update_time":"1509951256","title":"双胞胎测试iPhone X面部识别，双胞胎和面部识别技术的人机","view":"0","vid":"d48381af1b0b46598d3641290b653f5c","create_time":"1509950520","comment":"0","cid":"129","uid":"24","status":"1","nickname":"相信科学"}]
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
         * cover : http://static.mayinews.com/Uploads/Picture/2017-11-06/5a003046f241b.jpeg
         * size : 19666510
         * desc :
         * duration : 821
         * update_time : 1509961804
         * title : 开心麻花沈腾马丽小品《投其所好》
         * view : 0
         * vid : fdad46a691e24d76b9061991c0c48628
         * create_time : 1509961319
         * comment : 0
         * cid : 130
         * uid : 18
         * status : 1
         * nickname : 我们一起看综艺
         */

        private String cover;
        private String size;
        private String desc;
        private String duration;
        private String update_time;
        private String title;
        private String view;
        private String vid;
        private String create_time;
        private String comment;
        private String cid;
        private String uid;
        private String status;
        private String nickname;

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getView() {
            return view;
        }

        public void setView(String view) {
            this.view = view;
        }

        public String getVid() {
            return vid;
        }

        public void setVid(String vid) {
            this.vid = vid;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }
}
