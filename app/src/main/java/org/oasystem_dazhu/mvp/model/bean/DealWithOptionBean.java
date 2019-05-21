package org.oasystem_dazhu.mvp.model.bean;

import java.util.List;

/**
 * Created by www on 2019/5/22.
 */

public class DealWithOptionBean {


    /**
     * id : 23
     * user_id : 1
     * company_id : 1
     * form : 68be000f6ea6c9c821664543e7318ce9.doc
     * form_source_id : 1077
     * organ : asdsada
     * name : asdsadsa
     * serial : asdsad
     * number : asdsadsa
     * urgent : 0
     * principal_id : 1
     * main_body : null
     * main_body_source_id : null
     * accessory : 1555259766_测试.pdf
     * accessory_source_id : 1076
     * flow_type : 2
     * status : 0
     * form_type : 1
     * created_at : 2019-04-15 00:36:15
     * updated_at : 2019-04-15 00:36:15
     * office_form_id : 1077
     * sort : asc
     * biaoshi : 
     * year : 2019
     * dispatch_type_id : 0
     * approver : cs3[审批]
     * dispatch_suggest : [{"id":2,"content":"12312312","company_id":1,"created_at":"2019-05-09 21:47:45","updated_at":"2019-05-09 21:47:44","dispatch_id":23,"user_id":39,"flow_id":32,"user":{"id":39,"name":"cs3","phone":18090773460,"headimg":"http://www.fly.com/admin/img/head_img.jpeg","department_id":1,"position_id":3},"flow":{"id":32,"user_id":39,"dispatch_id":23,"number":null,"level":null,"company_id":1,"created_at":"2019-04-15 00:36:15","updated_at":"2019-04-15 00:36:15","status":0,"form_source_id":1077,"accessory_source_id":"1076","is_examine":1,"dispatch_name":"asdsadsa","form_type":1,"dispatch_serial":"asdsad","dispatch_organ":"asdsada","dispatch_number":"asdsadsa","dispatch_urgent":0,"dispatch_principal_id":1,"name":"自定义","auth":"1,2,3,4,5","tier":100,"year":"2019","remark":"asdsads","dispatch_type_id":"asdas"}}]
     */

    private String id;
    private String user_id;
    private String company_id;
    private String form;
    private String form_source_id;
    private String organ;
    private String name;
    private String serial;
    private String number;
    private String urgent;
    private String principal_id;
    private String main_body;
    private String main_body_source_id;
    private String accessory;
    private String accessory_source_id;
    private String flow_type;
    private String status;
    private String form_type;
    private String created_at;
    private String updated_at;
    private String office_form_id;
    private String sort;
    private String biaoshi;
    private String year;
    private String dispatch_type_id;
    private String approver;
    private List<DispatchSuggestBean> dispatch_suggest;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getForm_source_id() {
        return form_source_id;
    }

    public void setForm_source_id(String form_source_id) {
        this.form_source_id = form_source_id;
    }

    public String getOrgan() {
        return organ;
    }

    public void setOrgan(String organ) {
        this.organ = organ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUrgent() {
        return urgent;
    }

    public void setUrgent(String urgent) {
        this.urgent = urgent;
    }

    public String getPrincipal_id() {
        return principal_id;
    }

    public void setPrincipal_id(String principal_id) {
        this.principal_id = principal_id;
    }

    public String getMain_body() {
        return main_body;
    }

    public void setMain_body(String main_body) {
        this.main_body = main_body;
    }

    public String getMain_body_source_id() {
        return main_body_source_id;
    }

    public void setMain_body_source_id(String main_body_source_id) {
        this.main_body_source_id = main_body_source_id;
    }

    public String getAccessory() {
        return accessory;
    }

    public void setAccessory(String accessory) {
        this.accessory = accessory;
    }

    public String getAccessory_source_id() {
        return accessory_source_id;
    }

    public void setAccessory_source_id(String accessory_source_id) {
        this.accessory_source_id = accessory_source_id;
    }

    public String getFlow_type() {
        return flow_type;
    }

    public void setFlow_type(String flow_type) {
        this.flow_type = flow_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getForm_type() {
        return form_type;
    }

    public void setForm_type(String form_type) {
        this.form_type = form_type;
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

    public String getOffice_form_id() {
        return office_form_id;
    }

    public void setOffice_form_id(String office_form_id) {
        this.office_form_id = office_form_id;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getBiaoshi() {
        return biaoshi;
    }

    public void setBiaoshi(String biaoshi) {
        this.biaoshi = biaoshi;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDispatch_type_id() {
        return dispatch_type_id;
    }

    public void setDispatch_type_id(String dispatch_type_id) {
        this.dispatch_type_id = dispatch_type_id;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public List<DispatchSuggestBean> getDispatch_suggest() {
        return dispatch_suggest;
    }

    public void setDispatch_suggest(List<DispatchSuggestBean> dispatch_suggest) {
        this.dispatch_suggest = dispatch_suggest;
    }

    public static class DispatchSuggestBean {
        /**
         * id : 2
         * content : 12312312
         * company_id : 1
         * created_at : 2019-05-09 21:47:45
         * updated_at : 2019-05-09 21:47:44
         * dispatch_id : 23
         * user_id : 39
         * flow_id : 32
         * user : {"id":39,"name":"cs3","phone":18090773460,"headimg":"http://www.fly.com/admin/img/head_img.jpeg","department_id":1,"position_id":3}
         * flow : {"id":32,"user_id":39,"dispatch_id":23,"number":null,"level":null,"company_id":1,"created_at":"2019-04-15 00:36:15","updated_at":"2019-04-15 00:36:15","status":0,"form_source_id":1077,"accessory_source_id":"1076","is_examine":1,"dispatch_name":"asdsadsa","form_type":1,"dispatch_serial":"asdsad","dispatch_organ":"asdsada","dispatch_number":"asdsadsa","dispatch_urgent":0,"dispatch_principal_id":1,"name":"自定义","auth":"1,2,3,4,5","tier":100,"year":"2019","remark":"asdsads","dispatch_type_id":"asdas"}
         */

        private String id;
        private String content;
        private String company_id;
        private String created_at;
        private String updated_at;
        private String dispatch_id;
        private String user_id;
        private String flow_id;
        private UserBean user;
        private FlowBean flow;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCompany_id() {
            return company_id;
        }

        public void setCompany_id(String company_id) {
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

        public String getDispatch_id() {
            return dispatch_id;
        }

        public void setDispatch_id(String dispatch_id) {
            this.dispatch_id = dispatch_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getFlow_id() {
            return flow_id;
        }

        public void setFlow_id(String flow_id) {
            this.flow_id = flow_id;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public FlowBean getFlow() {
            return flow;
        }

        public void setFlow(FlowBean flow) {
            this.flow = flow;
        }

        public static class UserBean {
            /**
             * id : 39
             * name : cs3
             * phone : 18090773460
             * headimg : http://www.fly.com/admin/img/head_img.jpeg
             * department_id : 1
             * position_id : 3
             */

            private String id;
            private String name;
            private String phone;
            private String headimg;
            private String department_id;
            private String position_id;

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

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getHeadimg() {
                return headimg;
            }

            public void setHeadimg(String headimg) {
                this.headimg = headimg;
            }

            public String getDepartment_id() {
                return department_id;
            }

            public void setDepartment_id(String department_id) {
                this.department_id = department_id;
            }

            public String getPosition_id() {
                return position_id;
            }

            public void setPosition_id(String position_id) {
                this.position_id = position_id;
            }
        }

        public static class FlowBean {
            /**
             * id : 32
             * user_id : 39
             * dispatch_id : 23
             * number : null
             * level : null
             * company_id : 1
             * created_at : 2019-04-15 00:36:15
             * updated_at : 2019-04-15 00:36:15
             * status : 0
             * form_source_id : 1077
             * accessory_source_id : 1076
             * is_examine : 1
             * dispatch_name : asdsadsa
             * form_type : 1
             * dispatch_serial : asdsad
             * dispatch_organ : asdsada
             * dispatch_number : asdsadsa
             * dispatch_urgent : 0
             * dispatch_principal_id : 1
             * name : 自定义
             * auth : 1,2,3,4,5
             * tier : 100
             * year : 2019
             * remark : asdsads
             * dispatch_type_id : asdas
             */

            private String id;
            private String user_id;
            private String dispatch_id;
            private String number;
            private String level;
            private String company_id;
            private String created_at;
            private String updated_at;
            private String status;
            private String form_source_id;
            private String accessory_source_id;
            private String is_examine;
            private String dispatch_name;
            private String form_type;
            private String dispatch_serial;
            private String dispatch_organ;
            private String dispatch_number;
            private String dispatch_urgent;
            private String dispatch_principal_id;
            private String name;
            private String auth;
            private String tier;
            private String year;
            private String remark;
            private String dispatch_type_id;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getDispatch_id() {
                return dispatch_id;
            }

            public void setDispatch_id(String dispatch_id) {
                this.dispatch_id = dispatch_id;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public String getCompany_id() {
                return company_id;
            }

            public void setCompany_id(String company_id) {
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

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getForm_source_id() {
                return form_source_id;
            }

            public void setForm_source_id(String form_source_id) {
                this.form_source_id = form_source_id;
            }

            public String getAccessory_source_id() {
                return accessory_source_id;
            }

            public void setAccessory_source_id(String accessory_source_id) {
                this.accessory_source_id = accessory_source_id;
            }

            public String getIs_examine() {
                return is_examine;
            }

            public void setIs_examine(String is_examine) {
                this.is_examine = is_examine;
            }

            public String getDispatch_name() {
                return dispatch_name;
            }

            public void setDispatch_name(String dispatch_name) {
                this.dispatch_name = dispatch_name;
            }

            public String getForm_type() {
                return form_type;
            }

            public void setForm_type(String form_type) {
                this.form_type = form_type;
            }

            public String getDispatch_serial() {
                return dispatch_serial;
            }

            public void setDispatch_serial(String dispatch_serial) {
                this.dispatch_serial = dispatch_serial;
            }

            public String getDispatch_organ() {
                return dispatch_organ;
            }

            public void setDispatch_organ(String dispatch_organ) {
                this.dispatch_organ = dispatch_organ;
            }

            public String getDispatch_number() {
                return dispatch_number;
            }

            public void setDispatch_number(String dispatch_number) {
                this.dispatch_number = dispatch_number;
            }

            public String getDispatch_urgent() {
                return dispatch_urgent;
            }

            public void setDispatch_urgent(String dispatch_urgent) {
                this.dispatch_urgent = dispatch_urgent;
            }

            public String getDispatch_principal_id() {
                return dispatch_principal_id;
            }

            public void setDispatch_principal_id(String dispatch_principal_id) {
                this.dispatch_principal_id = dispatch_principal_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAuth() {
                return auth;
            }

            public void setAuth(String auth) {
                this.auth = auth;
            }

            public String getTier() {
                return tier;
            }

            public void setTier(String tier) {
                this.tier = tier;
            }

            public String getYear() {
                return year;
            }

            public void setYear(String year) {
                this.year = year;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getDispatch_type_id() {
                return dispatch_type_id;
            }

            public void setDispatch_type_id(String dispatch_type_id) {
                this.dispatch_type_id = dispatch_type_id;
            }
        }
    }
}
