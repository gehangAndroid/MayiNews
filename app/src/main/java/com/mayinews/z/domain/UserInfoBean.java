package com.mayinews.z.domain;

/**
 * Created by Administrator on 2018/2/6 0006.
 */

public class UserInfoBean {


    /**
     * status : 200
     * result : {"id":"16","nickname":"","gender":"1","avatar":"","desc":""}
     */

    private int status;
    private ResultBean result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 16
         * nickname :
         * gender : 1
         * avatar :
         * desc :
         */

        private String id;
        private String nickname;
        private String gender;
        private String avatar;
        private String desc;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "id='" + id + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", gender='" + gender + '\'' +
                    ", avatar='" + avatar + '\'' +
                    ", desc='" + desc + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "UserInfoBean{" +
                "status=" + status +
                ", result=" + result +
                '}';
    }
}
