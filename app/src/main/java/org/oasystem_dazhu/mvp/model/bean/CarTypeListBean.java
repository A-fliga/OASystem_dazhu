package org.oasystem_dazhu.mvp.model.bean;

import java.util.List;

/**
 * Created by www on 2019/3/23.
 */

public class CarTypeListBean {


    /**
     * status : 1
     * data : [{"id":1,"name":"公用车","company_id":1,"created_at":"2019-03-08 20:34:47","updated_at":"2019-03-08 20:34:47"},{"id":2,"name":"私用车","company_id":1,"created_at":"2019-03-08 20:34:54","updated_at":"2019-03-08 20:34:54"},{"id":3,"name":"特别用车","company_id":1,"created_at":"2019-03-08 20:34:59","updated_at":"2019-03-08 20:34:59"}]
     * msg : 操作成功
     * code : 0
     */

    private int status;
    private String msg;
    private int code;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * name : 公用车
         * company_id : 1
         * created_at : 2019-03-08 20:34:47
         * updated_at : 2019-03-08 20:34:47
         */

        private int id;
        private String name;
        private int company_id;
        private String created_at;
        private String updated_at;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCompany_id() {
            return company_id;
        }

        public void setCompany_id(int company_id) {
            this.company_id = company_id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }
    }
}
