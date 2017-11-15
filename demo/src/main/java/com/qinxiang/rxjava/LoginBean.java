package com.qinxiang.rxjava;

/**
 * Created by gouzhun on 2016/12/1.
 */
public class LoginBean{

    /**
     * accessToken : 10982.1480576474.1.87be28b700
     * sign : eJxljkFPgzAARu-8iqbXGVMKFTDxMB0pGw4miMFTg1Bmh2UNFONm-O8qasR4fu-L914NAAC8vU5Pi7LcD61m*qA4BOcAInjyC5USFSs0s7rqH*QvSnScFbXm3QhNQghGaOqIirda1OLHQJ6LJ7ivGjZ*fFH7Y4xcbJtTRWxHuPazqyU9xP2abvM0KNoooXF-lMPSGc6yEl-64SZbqId7fow2VpjPhT-HTuDLhRushiimNmm8NLnJy-RJrfRMhslj3cTybkZ3O91fTC61kPw7yLFs5OE-zc*868W*HQWMTGJi6zMbQePNeAcri1v9
     * loginStatus : 1
     * isComplete : 1
     * user : {"id":10982,"nickName":"😄😬☠️","head":"http://ishow-10027808.file.myqcloud.com/head/109821478141504_b.png","userLevel":2,"userExp":4189,"vipLevel":1,"vipExpiryTime":1480666194,"liveLevel":"5","cover":"http://ishow-10027808.file.myqcloud.com/cover/109821478080576.png","followCnt":10,"fansCnt":14,"coin":4394,"guildId":0,"guildName":"暂无家族","specialPay":false}
     * time : 1480576474
     * ip : 114.84.164.202
     */

    private DataBean data;
    /**
     * data : {"accessToken":"10982.1480576474.1.87be28b700","sign":"eJxljkFPgzAARu-8iqbXGVMKFTDxMB0pGw4miMFTg1Bmh2UNFONm-O8qasR4fu-L914NAAC8vU5Pi7LcD61m*qA4BOcAInjyC5USFSs0s7rqH*QvSnScFbXm3QhNQghGaOqIirda1OLHQJ6LJ7ivGjZ*fFH7Y4xcbJtTRWxHuPazqyU9xP2abvM0KNoooXF-lMPSGc6yEl-64SZbqId7fow2VpjPhT-HTuDLhRushiimNmm8NLnJy-RJrfRMhslj3cTybkZ3O91fTC61kPw7yLFs5OE-zc*868W*HQWMTGJi6zMbQePNeAcri1v9","loginStatus":1,"isComplete":1,"user":{"id":10982,"nickName":"😄😬☠️","head":"http://ishow-10027808.file.myqcloud.com/head/109821478141504_b.png","userLevel":2,"userExp":4189,"vipLevel":1,"vipExpiryTime":1480666194,"liveLevel":"5","cover":"http://ishow-10027808.file.myqcloud.com/cover/109821478080576.png","followCnt":10,"fansCnt":14,"coin":4394,"guildId":0,"guildName":"暂无家族","specialPay":false},"time":1480576474,"ip":"114.84.164.202"}
     * error :
     * status : 1
     */

    private String error;
    private int status;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "data=" + data +
                ", error='" + error + '\'' +
                ", status=" + status +
                '}';
    }

    public static class DataBean {
        private String accessToken;
        private String sign;
        private int loginStatus;
        private int isComplete;

        @Override
        public String toString() {
            return "DataBean{" +
                    "accessToken='" + accessToken + '\'' +
                    ", sign='" + sign + '\'' +
                    ", loginStatus=" + loginStatus +
                    ", isComplete=" + isComplete +
                    ", user=" + user +
                    ", time=" + time +
                    ", ip='" + ip + '\'' +
                    '}';
        }

        /**
         * id : 10982
         * nickName : 😄😬☠️
         * head : http://ishow-10027808.file.myqcloud.com/head/109821478141504_b.png
         * userLevel : 2
         * userExp : 4189
         * vipLevel : 1
         * vipExpiryTime : 1480666194
         * liveLevel : 5
         * cover : http://ishow-10027808.file.myqcloud.com/cover/109821478080576.png
         * followCnt : 10
         * fansCnt : 14
         * coin : 4394
         * guildId : 0
         * guildName : 暂无家族
         * specialPay : false
         */



        private UserBean user;
        private int time;
        private String ip;

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public int getLoginStatus() {
            return loginStatus;
        }

        public void setLoginStatus(int loginStatus) {
            this.loginStatus = loginStatus;
        }

        public int getIsComplete() {
            return isComplete;
        }

        public void setIsComplete(int isComplete) {
            this.isComplete = isComplete;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }



        public static class UserBean {
            private int id;
            private String nickName;
            private String head;
            private int userLevel;
            private int userExp;
            private int vipLevel;
            private int vipExpiryTime;
            private String liveLevel;
            private String cover;
            private int followCnt;
            private int fansCnt;
            private int coin;
            private int guildId;
            private String guildName;
            private boolean specialPay;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getHead() {
                return head;
            }

            public void setHead(String head) {
                this.head = head;
            }

            public int getUserLevel() {
                return userLevel;
            }

            public void setUserLevel(int userLevel) {
                this.userLevel = userLevel;
            }

            public int getUserExp() {
                return userExp;
            }

            public void setUserExp(int userExp) {
                this.userExp = userExp;
            }

            public int getVipLevel() {
                return vipLevel;
            }

            public void setVipLevel(int vipLevel) {
                this.vipLevel = vipLevel;
            }

            public int getVipExpiryTime() {
                return vipExpiryTime;
            }

            public void setVipExpiryTime(int vipExpiryTime) {
                this.vipExpiryTime = vipExpiryTime;
            }

            public String getLiveLevel() {
                return liveLevel;
            }

            public void setLiveLevel(String liveLevel) {
                this.liveLevel = liveLevel;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public int getFollowCnt() {
                return followCnt;
            }

            public void setFollowCnt(int followCnt) {
                this.followCnt = followCnt;
            }

            public int getFansCnt() {
                return fansCnt;
            }

            public void setFansCnt(int fansCnt) {
                this.fansCnt = fansCnt;
            }

            public int getCoin() {
                return coin;
            }

            public void setCoin(int coin) {
                this.coin = coin;
            }

            public int getGuildId() {
                return guildId;
            }

            public void setGuildId(int guildId) {
                this.guildId = guildId;
            }

            public String getGuildName() {
                return guildName;
            }

            public void setGuildName(String guildName) {
                this.guildName = guildName;
            }

            public boolean isSpecialPay() {
                return specialPay;
            }

            public void setSpecialPay(boolean specialPay) {
                this.specialPay = specialPay;
            }


            @Override
            public String toString() {
                return "UserBean{" +
                        "coin=" + coin +
                        ", id=" + id +
                        ", nickName='" + nickName + '\'' +
                        ", head='" + head + '\'' +
                        ", userLevel=" + userLevel +
                        ", userExp=" + userExp +
                        ", vipLevel=" + vipLevel +
                        ", vipExpiryTime=" + vipExpiryTime +
                        ", liveLevel='" + liveLevel + '\'' +
                        ", cover='" + cover + '\'' +
                        ", followCnt=" + followCnt +
                        ", fansCnt=" + fansCnt +
                        ", guildId=" + guildId +
                        ", guildName='" + guildName + '\'' +
                        ", specialPay=" + specialPay +
                        '}';
            }
        }
    }
}
