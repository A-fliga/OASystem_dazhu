package org.oasystem_dazhu.mvp.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by www on 2019/1/6.
 */

public class DocumentBean implements Serializable {


    /**
     * current_page : 1
     * first_page_url : http://oa.zycnb.net/api/dispatch/wait?page=1
     * from : 1
     * last_page : 1
     * last_page_url : http://oa.zycnb.net/api/dispatch/wait?page=1
     * next_page_url : asda
     * path : http://oa.zycnb.net/api/dispatch/wait
     * per_page : 20
     * prev_page_url : asdads
     * to : 1
     * total : 1
     */

    private int current_page;
    private String first_page_url;
    private int from;
    private int last_page;
    private String last_page_url;
    private String next_page_url;
    private String path;
    private int per_page;
    private String prev_page_url;
    private int to;
    private int total;
    private List<DataBean> data;


    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public String getFirst_page_url() {
        return first_page_url;
    }

    public void setFirst_page_url(String first_page_url) {
        this.first_page_url = first_page_url;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getLast_page() {
        return last_page;
    }

    public void setLast_page(int last_page) {
        this.last_page = last_page;
    }

    public String getLast_page_url() {
        return last_page_url;
    }

    public void setLast_page_url(String last_page_url) {
        this.last_page_url = last_page_url;
    }

    public String getNext_page_url() {
        return next_page_url;
    }

    public void setNext_page_url(String next_page_url) {
        this.next_page_url = next_page_url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public String getPrev_page_url() {
        return prev_page_url;
    }

    public void setPrev_page_url(String prev_page_url) {
        this.prev_page_url = prev_page_url;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * id : 55
         * user_id : 8
         * dispatch_id : 23
         * level : 1
         * company_id : 1
         * created_at : 2019-01-06 16:34:16
         * updated_at : 2019-01-06 16:34:16
         * status : 0
         * form_source_id : 0
         * accessory_source_id : 0
         * is_examine : 1
         * user : {"id":8,"name":"测试账号1","phone":1212,"headimg":"http://www.fly.com/admin/img/head_img.jpeg","department_id":4,"position_id":2,"position":{"id":2,"name":"职务2","level":1,"company_id":1,"created_at":"2018-12-21 13:53:55","updated_at":"2018-12-21 13:53:55"},"department":{"id":4,"name":"部门置顶","parent_id":1,"sort":1,"level":2,"company_id":1,"created_at":"2018-12-20 19:29:54","updated_at":"2018-12-20 19:36:13"}}
         * dispatch : {"id":23,"user_id":1,"company_id":1,"form":"asda","form_source_id":22,"organ":"2123","name":"123123123","serial":"123123123","main_body":"asdssad","main_body_source_id":"asdsad","accessory":"test.doc","accessory_source_id":"140","flow_type":2,"status":0,"created_at":"2019-01-06 16:34:16","updated_at":"2019-01-06 16:34:16","accessory_list":[{"name":"test.doc","source_id":"140"}],"approver":"测试账号1","user":{"id":1,"name":"公司测试c","phone":15308057727,"headimg":"http://www.fly.com/uploads/images/2018-12-24/199ed138bd0dfcf55681876ccad63873.jpeg","department_id":0,"position_id":0},"company":{"id":1,"name":"公司测试c","account":"admin","phone":15308057727,"mail":"asda","status":1,"headimg":"http://www.fly.com/uploads/images/2018-12-24/199ed138bd0dfcf55681876ccad63873.jpeg","password":"$2y$10$waVOwy1nmMD.OnMERxnXJOnH9EjrTmxxNnEwYLdPDCIFiKOibelB.","remark":"asdd","is_admin":1,"company_id":1,"department_id":0,"position_id":0,"company_name":"单位1","remember_token":"kTe0bPKiO6VMqnudyTrpQoM6HKisJNooqCGfHcjpNGDmHoseQQzGCMYKgvpf","created_at":"asdsada","updated_at":"2018-12-24 22:14:46"},"form_source":{"id":22,"name":"发文.docx","user_id":1,"company_id":1,"path":"1/1/发文.docx","created_at":"2018-12-25 01:11:16","updated_at":"2018-12-25 01:11:16"},"flows":[{"id":55,"user_id":"8","dispatch_id":23,"level":1,"company_id":1,"created_at":"2019-01-06 16:34:16","updated_at":"2019-01-06 16:34:16","status":0,"form_source_id":0,"accessory_source_id":0,"is_examine":1,"user":{"id":8,"name":"测试账号1","phone":1212,"headimg":"http://www.fly.com/admin/img/head_img.jpeg","department_id":4,"position_id":2}},{"id":54,"user_id":"7","dispatch_id":23,"level":2,"company_id":1,"created_at":"2019-01-06 16:34:16","updated_at":"2019-01-06 16:34:16","status":0,"form_source_id":0,"accessory_source_id":0,"is_examine":0}]}
         */

        private int id;
        private String user_id;
        private int dispatch_id;
        private String level;
        private int company_id;
        private String created_at;
        private String updated_at;
        private int status;
        private String form_source_id;
        private String accessory_source_id;
        private int is_examine;
        private UserBean user;
        private DispatchBean dispatch;
        private String auth;
        private List<DataFlowsBean> flows;
        private String name;
        private String schedule;

        public String getSchedule() {
            return schedule;
        }

        public void setSchedule(String schedule) {
            this.schedule = schedule;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<DataFlowsBean> getFlows() {
            return flows;
        }

        public void setFlows(List<DataFlowsBean> flows) {
            this.flows = flows;
        }

        public static class DataFlowsBean implements Serializable {

            /**
             * id : 140
             * user_id : 10
             * dispatch_id : 83
             * level : 3
             * company_id : 1
             * created_at : 2019-01-17 00:36:13
             * updated_at : 2019-01-17 00:36:13
             * status : 0
             * form_source_id : 181
             * accessory_source_id : 268
             * is_examine : 1
             * dispatch_name : 辅导辅导
             * form_type : 3
             * dispatch_serial : 123452019
             * dispatch_organ : 方式发顺丰
             * dispatch_number : 123456
             * dispatch_urgent : 0
             * dispatch_principal_id : 8
             * name : 自定义
             * auth : 1,2,3,4,5
             * user : {"id":10,"name":"测试3","phone":111,"headimg":"http://oa.zycnb.net/uploads/images/2019-01-08/029c60c7993de4c644885c641f18f30f.jpg","department_id":1,"position_id":3}
             */

            private int id;
            private int user_id;
            private int dispatch_id;
            private String level;
            private int company_id;
            private String created_at;
            private String updated_at;
            private int status;
            private int form_source_id;
            private String accessory_source_id;
            private int is_examine;
            private String dispatch_name;
            private int form_type;
            private String dispatch_serial;
            private String dispatch_organ;
            private String dispatch_number;
            private int dispatch_urgent;
            private int dispatch_principal_id;
            private String name;
            private String auth;
            private UserBeanXXXX user;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public int getDispatch_id() {
                return dispatch_id;
            }

            public void setDispatch_id(int dispatch_id) {
                this.dispatch_id = dispatch_id;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
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

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getForm_source_id() {
                return form_source_id;
            }

            public void setForm_source_id(int form_source_id) {
                this.form_source_id = form_source_id;
            }

            public String getAccessory_source_id() {
                return accessory_source_id;
            }

            public void setAccessory_source_id(String accessory_source_id) {
                this.accessory_source_id = accessory_source_id;
            }

            public int getIs_examine() {
                return is_examine;
            }

            public void setIs_examine(int is_examine) {
                this.is_examine = is_examine;
            }

            public String getDispatch_name() {
                return dispatch_name;
            }

            public void setDispatch_name(String dispatch_name) {
                this.dispatch_name = dispatch_name;
            }

            public int getForm_type() {
                return form_type;
            }

            public void setForm_type(int form_type) {
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

            public int getDispatch_urgent() {
                return dispatch_urgent;
            }

            public void setDispatch_urgent(int dispatch_urgent) {
                this.dispatch_urgent = dispatch_urgent;
            }

            public int getDispatch_principal_id() {
                return dispatch_principal_id;
            }

            public void setDispatch_principal_id(int dispatch_principal_id) {
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

            public UserBeanXXXX getUser() {
                return user;
            }

            public void setUser(UserBeanXXXX user) {
                this.user = user;
            }

            public static class UserBeanXXXX implements Serializable {
                /**
                 * id : 10
                 * name : 测试3
                 * phone : 111
                 * headimg : http://oa.zycnb.net/uploads/images/2019-01-08/029c60c7993de4c644885c641f18f30f.jpg
                 * department_id : 1
                 * position_id : 3
                 */

                private int id;
                private String name;
                private String phone;
                private String headimg;
                private int department_id;
                private int position_id;

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

                public int getDepartment_id() {
                    return department_id;
                }

                public void setDepartment_id(int department_id) {
                    this.department_id = department_id;
                }

                public int getPosition_id() {
                    return position_id;
                }

                public void setPosition_id(int position_id) {
                    this.position_id = position_id;
                }
            }
        }

        public String getAuth() {
            return auth;
        }

        public void setAuth(String auth) {
            this.auth = auth;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public int getDispatch_id() {
            return dispatch_id;
        }

        public void setDispatch_id(int dispatch_id) {
            this.dispatch_id = dispatch_id;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
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

        public int getIs_examine() {
            return is_examine;
        }

        public void setIs_examine(int is_examine) {
            this.is_examine = is_examine;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public DispatchBean getDispatch() {
            return dispatch;
        }

        public void setDispatch(DispatchBean dispatch) {
            this.dispatch = dispatch;
        }

        public static class UserBean implements Serializable {
            /**
             * id : 8
             * name : 测试账号1
             * phone : 1212
             * headimg : http://www.fly.com/admin/img/head_img.jpeg
             * department_id : 4
             * position_id : 2
             * position : {"id":2,"name":"职务2","level":1,"company_id":1,"created_at":"2018-12-21 13:53:55","updated_at":"2018-12-21 13:53:55"}
             * department : {"id":4,"name":"部门置顶","parent_id":1,"sort":1,"level":2,"company_id":1,"created_at":"2018-12-20 19:29:54","updated_at":"2018-12-20 19:36:13"}
             */

            private int id;
            private String name;
            private String phone;
            private String headimg;
            private int department_id;
            private int position_id;
            private PositionBean position;
            private DepartmentBean department;

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

            public int getDepartment_id() {
                return department_id;
            }

            public void setDepartment_id(int department_id) {
                this.department_id = department_id;
            }

            public int getPosition_id() {
                return position_id;
            }

            public void setPosition_id(int position_id) {
                this.position_id = position_id;
            }

            public PositionBean getPosition() {
                return position;
            }

            public void setPosition(PositionBean position) {
                this.position = position;
            }

            public DepartmentBean getDepartment() {
                return department;
            }

            public void setDepartment(DepartmentBean department) {
                this.department = department;
            }

            public static class PositionBean implements Serializable {
                /**
                 * id : 2
                 * name : 职务2
                 * level : 1
                 * company_id : 1
                 * created_at : 2018-12-21 13:53:55
                 * updated_at : 2018-12-21 13:53:55
                 */

                private int id;
                private String name;
                private String level;
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

                public String getLevel() {
                    return level;
                }

                public void setLevel(String level) {
                    this.level = level;
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

            public static class DepartmentBean implements Serializable {
                /**
                 * id : 4
                 * name : 部门置顶
                 * parent_id : 1
                 * sort : 1
                 * level : 2
                 * company_id : 1
                 * created_at : 2018-12-20 19:29:54
                 * updated_at : 2018-12-20 19:36:13
                 */

                private int id;
                private String name;
                private int parent_id;
                private int sort;
                private String level;
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

                public int getParent_id() {
                    return parent_id;
                }

                public void setParent_id(int parent_id) {
                    this.parent_id = parent_id;
                }

                public int getSort() {
                    return sort;
                }

                public void setSort(int sort) {
                    this.sort = sort;
                }

                public String getLevel() {
                    return level;
                }

                public void setLevel(String level) {
                    this.level = level;
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


        public static class DispatchBean implements Serializable {
            /**
             * id : 23
             * user_id : 1
             * company_id : 1
             * form : asda
             * form_source_id : 22
             * organ : 2123
             * name : 123123123
             * serial : 123123123
             * main_body : asdssad
             * main_body_source_id : asdsad
             * accessory : test.doc
             * accessory_source_id : 140
             * flow_type : 2
             * status : 0
             * created_at : 2019-01-06 16:34:16
             * updated_at : 2019-01-06 16:34:16
             * accessory_list : [{"name":"test.doc","source_id":"140"}]
             * approver : 测试账号1
             * user : {"id":1,"name":"公司测试c","phone":15308057727,"headimg":"http://www.fly.com/uploads/images/2018-12-24/199ed138bd0dfcf55681876ccad63873.jpeg","department_id":0,"position_id":0}
             * company : {"id":1,"name":"公司测试c","account":"admin","phone":15308057727,"mail":"asda","status":1,"headimg":"http://www.fly.com/uploads/images/2018-12-24/199ed138bd0dfcf55681876ccad63873.jpeg","password":"$2y$10$waVOwy1nmMD.OnMERxnXJOnH9EjrTmxxNnEwYLdPDCIFiKOibelB.","remark":"asdd","is_admin":1,"company_id":1,"department_id":0,"position_id":0,"company_name":"单位1","remember_token":"kTe0bPKiO6VMqnudyTrpQoM6HKisJNooqCGfHcjpNGDmHoseQQzGCMYKgvpf","created_at":"asdsada","updated_at":"2018-12-24 22:14:46"}
             * form_source : {"id":22,"name":"发文.docx","user_id":1,"company_id":1,"path":"1/1/发文.docx","created_at":"2018-12-25 01:11:16","updated_at":"2018-12-25 01:11:16"}
             * flows : [{"id":55,"user_id":"8","dispatch_id":23,"level":1,"company_id":1,"created_at":"2019-01-06 16:34:16","updated_at":"2019-01-06 16:34:16","status":0,"form_source_id":0,"accessory_source_id":0,"is_examine":1,"user":{"id":8,"name":"测试账号1","phone":1212,"headimg":"http://www.fly.com/admin/img/head_img.jpeg","department_id":4,"position_id":2}},{"id":54,"user_id":"7","dispatch_id":23,"level":2,"company_id":1,"created_at":"2019-01-06 16:34:16","updated_at":"2019-01-06 16:34:16","status":0,"form_source_id":0,"accessory_source_id":0,"is_examine":0}]
             */

            private int id;
            private int user_id;
            private int company_id;
            private String form;
            private int form_source_id;
            private String organ;
            private String name;
            private String serial;
            private String main_body;
            private String main_body_source_id;
            private String accessory;
            private String accessory_source_id;
            private int flow_type;
            private int status;
            private String status_name;
            private String created_at;
            private String updated_at;
            private String approver;
            private UserBeanX user;
            private CompanyBean company;
            private FormSourceBean form_source;
            private List<AccessoryListBean> accessory_list;
            private List<FlowsBean> flows;
            private int urgent;
            private int form_type;
            private String urgent_date;

            public String getUrgent_date() {
                return urgent_date;
            }

            public void setUrgent_date(String urgent_date) {
                this.urgent_date = urgent_date;
            }

            public String getStatus_name() {
                return status_name;
            }

            public void setStatus_name(String status_name) {
                this.status_name = status_name;
            }

            public int getForm_type() {
                return form_type;
            }

            public void setForm_type(int form_type) {
                this.form_type = form_type;
            }

            public int getUrgent() {
                return urgent;
            }

            public void setUrgent(int urgent) {
                this.urgent = urgent;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public int getCompany_id() {
                return company_id;
            }

            public void setCompany_id(int company_id) {
                this.company_id = company_id;
            }

            public String getForm() {
                return form;
            }

            public void setForm(String form) {
                this.form = form;
            }

            public int getForm_source_id() {
                return form_source_id;
            }

            public void setForm_source_id(int form_source_id) {
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

            public int getFlow_type() {
                return flow_type;
            }

            public void setFlow_type(int flow_type) {
                this.flow_type = flow_type;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
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

            public String getApprover() {
                return approver;
            }

            public void setApprover(String approver) {
                this.approver = approver;
            }

            public UserBeanX getUser() {
                return user;
            }

            public void setUser(UserBeanX user) {
                this.user = user;
            }

            public CompanyBean getCompany() {
                return company;
            }

            public void setCompany(CompanyBean company) {
                this.company = company;
            }

            public FormSourceBean getForm_source() {
                return form_source;
            }

            public void setForm_source(FormSourceBean form_source) {
                this.form_source = form_source;
            }

            public List<AccessoryListBean> getAccessory_list() {
                return accessory_list;
            }

            public void setAccessory_list(List<AccessoryListBean> accessory_list) {
                this.accessory_list = accessory_list;
            }

            public List<FlowsBean> getFlows() {
                return flows;
            }

            public void setFlows(List<FlowsBean> flows) {
                this.flows = flows;
            }

            public static class UserBeanX implements Serializable {
                /**
                 * id : 1
                 * name : 公司测试c
                 * phone : 15308057727
                 * headimg : http://www.fly.com/uploads/images/2018-12-24/199ed138bd0dfcf55681876ccad63873.jpeg
                 * department_id : 0
                 * position_id : 0
                 */

                private int id;
                private String name;
                private String phone;
                private String headimg;
                private int department_id;
                private int position_id;

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

                public int getDepartment_id() {
                    return department_id;
                }

                public void setDepartment_id(int department_id) {
                    this.department_id = department_id;
                }

                public int getPosition_id() {
                    return position_id;
                }

                public void setPosition_id(int position_id) {
                    this.position_id = position_id;
                }
            }

            public static class CompanyBean implements Serializable {
                /**
                 * id : 1
                 * name : 公司测试c
                 * account : admin
                 * phone : 15308057727
                 * mail : asda
                 * status : 1
                 * headimg : http://www.fly.com/uploads/images/2018-12-24/199ed138bd0dfcf55681876ccad63873.jpeg
                 * password : $2y$10$waVOwy1nmMD.OnMERxnXJOnH9EjrTmxxNnEwYLdPDCIFiKOibelB.
                 * remark : asdd
                 * is_admin : 1
                 * company_id : 1
                 * department_id : 0
                 * position_id : 0
                 * company_name : 单位1
                 * remember_token : kTe0bPKiO6VMqnudyTrpQoM6HKisJNooqCGfHcjpNGDmHoseQQzGCMYKgvpf
                 * created_at : asdsada
                 * updated_at : 2018-12-24 22:14:46
                 */

                private int id;
                private String name;
                private String account;
                private String phone;
                private String mail;
                private int status;
                private String headimg;
                private String password;
                private String remark;
                private int is_admin;
                private int company_id;
                private int department_id;
                private int position_id;
                private String company_name;
                private String remember_token;
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

                public String getAccount() {
                    return account;
                }

                public void setAccount(String account) {
                    this.account = account;
                }

                public String getPhone() {
                    return phone;
                }

                public void setPhone(String phone) {
                    this.phone = phone;
                }

                public String getMail() {
                    return mail;
                }

                public void setMail(String mail) {
                    this.mail = mail;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public String getHeadimg() {
                    return headimg;
                }

                public void setHeadimg(String headimg) {
                    this.headimg = headimg;
                }

                public String getPassword() {
                    return password;
                }

                public void setPassword(String password) {
                    this.password = password;
                }

                public String getRemark() {
                    return remark;
                }

                public void setRemark(String remark) {
                    this.remark = remark;
                }

                public int getIs_admin() {
                    return is_admin;
                }

                public void setIs_admin(int is_admin) {
                    this.is_admin = is_admin;
                }

                public int getCompany_id() {
                    return company_id;
                }

                public void setCompany_id(int company_id) {
                    this.company_id = company_id;
                }

                public int getDepartment_id() {
                    return department_id;
                }

                public void setDepartment_id(int department_id) {
                    this.department_id = department_id;
                }

                public int getPosition_id() {
                    return position_id;
                }

                public void setPosition_id(int position_id) {
                    this.position_id = position_id;
                }

                public String getCompany_name() {
                    return company_name;
                }

                public void setCompany_name(String company_name) {
                    this.company_name = company_name;
                }

                public String getRemember_token() {
                    return remember_token;
                }

                public void setRemember_token(String remember_token) {
                    this.remember_token = remember_token;
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

            public static class FormSourceBean implements Serializable {
                /**
                 * id : 22
                 * name : 发文.docx
                 * user_id : 1
                 * company_id : 1
                 * path : 1/1/发文.docx
                 * created_at : 2018-12-25 01:11:16
                 * updated_at : 2018-12-25 01:11:16
                 */

                private int id;
                private String name;
                private int user_id;
                private int company_id;
                private String path;
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

                public int getUser_id() {
                    return user_id;
                }

                public void setUser_id(int user_id) {
                    this.user_id = user_id;
                }

                public int getCompany_id() {
                    return company_id;
                }

                public void setCompany_id(int company_id) {
                    this.company_id = company_id;
                }

                public String getPath() {
                    return path;
                }

                public void setPath(String path) {
                    this.path = path;
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

            public static class AccessoryListBean implements Serializable {
                /**
                 * name : test.doc
                 * source_id : 140
                 */

                private String name;
                private String source_id;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getSource_id() {
                    return source_id;
                }

                public void setSource_id(String source_id) {
                    this.source_id = source_id;
                }
            }

            public static class FlowsBean implements Serializable {
                /**
                 * id : 115
                 * user_id : 8
                 * dispatch_id : asda
                 * level : asda
                 * company_id : 1
                 * created_at : 2019-01-09 12:59:33
                 * updated_at : 2019-01-19 21:14:57
                 * status : asdsa
                 * form_source_id : 330
                 * accessory_source_id : 323#328#43
                 * is_examine : 1
                 * dispatch_name : 测试123
                 * form_type : 1
                 * dispatch_serial : 测试123
                 * dispatch_organ : 测试123
                 * dispatch_number : 0
                 * dispatch_urgent : asda
                 * dispatch_principal_id : 8
                 * name : 自定义
                 * auth : 1,2,3,4,5
                 * user : {"id":8,"name":"测试1","phone":"asdsada","headimg":"http://oa.zycnb.net/uploads/images/2019-01-08/3a347815c69b699c8f1196e2a954d2a1.jpg","department_id":"asdsa","position_id":"asda"}
                 */

                private int id;
                private int user_id;
                private String dispatch_id;
                private String level;
                private int company_id;
                private String created_at;
                private String updated_at;
                private String status;
                private int form_source_id;
                private String accessory_source_id;
                private int is_examine;
                private String dispatch_name;
                private int form_type;
                private String dispatch_serial;
                private String dispatch_organ;
                private String dispatch_number;
                private String dispatch_urgent;
                private int dispatch_principal_id;
                private String name;
                private String auth;
                private UserBeanXX user;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getUser_id() {
                    return user_id;
                }

                public void setUser_id(int user_id) {
                    this.user_id = user_id;
                }

                public String getDispatch_id() {
                    return dispatch_id;
                }

                public void setDispatch_id(String dispatch_id) {
                    this.dispatch_id = dispatch_id;
                }

                public String getLevel() {
                    return level;
                }

                public void setLevel(String level) {
                    this.level = level;
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

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public int getForm_source_id() {
                    return form_source_id;
                }

                public void setForm_source_id(int form_source_id) {
                    this.form_source_id = form_source_id;
                }

                public String getAccessory_source_id() {
                    return accessory_source_id;
                }

                public void setAccessory_source_id(String accessory_source_id) {
                    this.accessory_source_id = accessory_source_id;
                }

                public int getIs_examine() {
                    return is_examine;
                }

                public void setIs_examine(int is_examine) {
                    this.is_examine = is_examine;
                }

                public String getDispatch_name() {
                    return dispatch_name;
                }

                public void setDispatch_name(String dispatch_name) {
                    this.dispatch_name = dispatch_name;
                }

                public int getForm_type() {
                    return form_type;
                }

                public void setForm_type(int form_type) {
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

                public int getDispatch_principal_id() {
                    return dispatch_principal_id;
                }

                public void setDispatch_principal_id(int dispatch_principal_id) {
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

                public UserBeanXX getUser() {
                    return user;
                }

                public void setUser(UserBeanXX user) {
                    this.user = user;
                }

                public static class UserBeanXX implements Serializable {
                    /**
                     * id : 8
                     * name : 测试1
                     * phone : asdsada
                     * headimg : http://oa.zycnb.net/uploads/images/2019-01-08/3a347815c69b699c8f1196e2a954d2a1.jpg
                     * department_id : asdsa
                     * position_id : asda
                     */

                    private int id;
                    private String name;
                    private String phone;
                    private String headimg;
                    private String department_id;
                    private String position_id;

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


//                /**
//                 * id : 55
//                 * user_id : 8
//                 * dispatch_id : 23
//                 * level : 1
//                 * company_id : 1
//                 * created_at : 2019-01-06 16:34:16
//                 * updated_at : 2019-01-06 16:34:16
//                 * status : 0
//                 * form_source_id : 0
//                 * accessory_source_id : 0
//                 * is_examine : 1
//                 * user : {"id":8,"name":"测试账号1","phone":1212,"headimg":"http://www.fly.com/admin/img/head_img.jpeg","department_id":4,"position_id":2}
//                 */
//
//                private int id;
//                private String user_id;
//                private int dispatch_id;
//                private String level;
//                private int company_id;
//                private String created_at;
//                private String updated_at;
//                private int status;
//                private int form_source_id;
//                private String accessory_source_id;
//                private int is_examine;
//                private UserBeanXX user;
//
//                public int getId() {
//                    return id;
//                }
//
//                public void setId(int id) {
//                    this.id = id;
//                }
//
//                public String getUser_id() {
//                    return user_id;
//                }
//
//                public void setUser_id(String user_id) {
//                    this.user_id = user_id;
//                }
//
//                public int getDispatch_id() {
//                    return dispatch_id;
//                }
//
//                public void setDispatch_id(int dispatch_id) {
//                    this.dispatch_id = dispatch_id;
//                }
//
//                public int getLevel() {
//                    return level;
//                }
//
//                public void setLevel(String level) {
//                    this.level = level;
//                }
//
//                public int getCompany_id() {
//                    return company_id;
//                }
//
//                public void setCompany_id(int company_id) {
//                    this.company_id = company_id;
//                }
//
//                public String getCreated_at() {
//                    return created_at;
//                }
//
//                public void setCreated_at(String created_at) {
//                    this.created_at = created_at;
//                }
//
//                public String getUpdated_at() {
//                    return updated_at;
//                }
//
//                public void setUpdated_at(String updated_at) {
//                    this.updated_at = updated_at;
//                }
//
//                public int getStatus() {
//                    return status;
//                }
//
//                public void setStatus(int status) {
//                    this.status = status;
//                }
//
//                public int getForm_source_id() {
//                    return form_source_id;
//                }
//
//                public void setForm_source_id(int form_source_id) {
//                    this.form_source_id = form_source_id;
//                }
//
//                public String getAccessory_source_id() {
//                    return accessory_source_id;
//                }
//
//                public void setAccessory_source_id(String accessory_source_id) {
//                    this.accessory_source_id = accessory_source_id;
//                }
//
//                public int getIs_examine() {
//                    return is_examine;
//                }
//
//                public void setIs_examine(int is_examine) {
//                    this.is_examine = is_examine;
//                }
//
//                public UserBeanXX getUser() {
//                    return user;
//                }
//
//                public void setUser(UserBeanXX user) {
//                    this.user = user;
//                }
//
//                public static class UserBeanXX implements Serializable {
//                    /**
//                     * id : 8
//                     * name : 测试账号1
//                     * phone : 1212
//                     * headimg : http://www.fly.com/admin/img/head_img.jpeg
//                     * department_id : 4
//                     * position_id : 2
//                     */
//
//                    private int id;
//                    private String name;
//                    private int phone;
//                    private String headimg;
//                    private int department_id;
//                    private int position_id;
//
//                    public int getId() {
//                        return id;
//                    }
//
//                    public void setId(int id) {
//                        this.id = id;
//                    }
//
//                    public String getName() {
//                        return name;
//                    }
//
//                    public void setName(String name) {
//                        this.name = name;
//                    }
//
//                    public int getPhone() {
//                        return phone;
//                    }
//
//                    public void setPhone(int phone) {
//                        this.phone = phone;
//                    }
//
//                    public String getHeadimg() {
//                        return headimg;
//                    }
//
//                    public void setHeadimg(String headimg) {
//                        this.headimg = headimg;
//                    }
//
//                    public int getDepartment_id() {
//                        return department_id;
//                    }
//
//                    public void setDepartment_id(int department_id) {
//                        this.department_id = department_id;
//                    }
//
//                    public int getPosition_id() {
//                        return position_id;
//                    }
//
//                    public void setPosition_id(int position_id) {
//                        this.position_id = position_id;
//                    }
//                }
//            }
            }
        }
    }
}
