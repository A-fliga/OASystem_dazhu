package org.oasystem_dazhu.mvp.model.bean;

import java.util.List;

/**
 * Created by www on 2019/2/16.
 */

public class UserInfo {

    /**
     * id : 7
     * name : 测试2
     * account : cs2
     * phone : asdsad
     * mail : asdada
     * status : 1
     * headimg : http://oa.zycnb.net/uploads/images/2019-01-08/4d897a9b5b9a62d586483488ba7629c0.jpg
     * remark : asdd
     * company_id : 1
     * department_id : 4
     * position_id : 5
     * company_name : asdd
     * created_at : 2018-12-18 15:23:29
     * office_seal_tpye : [{"id":1,"name":"印章类型1","company_id":1,"created_at":"2018-12-23 16:14:43","updated_at":"2018-12-23 16:14:43"},{"id":2,"name":"印章类型2","company_id":1,"created_at":"2018-12-23 16:14:50","updated_at":"2018-12-23 16:14:50"}]
     * is_monitoring : 1
     * department : {"id":4,"name":"二秘办公室","parent_id":1,"sort":1,"level":2,"company_id":1,"created_at":"2018-12-20 19:29:54","updated_at":"2019-01-25 10:09:21"}
     * position : {"id":5,"name":"办公室副主任","level":5,"company_id":1,"created_at":"2019-01-08 14:47:16","updated_at":"2019-01-25 10:10:22"}
     * office_seal : []
     * company : {"id":1,"name":"admin","account":"admin","phone":"asdada","mail":"asdada","status":1,"headimg":"http://oa.zycnb.net/uploads/images/2019-01-08/f0f17012fb5ac0b8771d823f7e4ff482.jpg","password":"$2y$10$X6qEGidOZGEDYzbcrUHOhe1ISLfs1BveeadLcHCF5uJBqq/tgLD9i","remark":"asdada","is_admin":1,"company_id":1,"department_id":0,"position_id":0,"company_name":"县政府","company_logo":"asdada","remember_token":"BmtVL9iGPQRAbIZFy4ire1MA5wtZSjfVKxSW29oZOT1nuQ9lWLdVGE0yXpFG","created_at":"asdada","updated_at":"2019-02-16 17:07:00","number":0,"app_v":"1.0.0","app_label":"第一版本","app_type":0,"app_path":"http://www.fly.com/uploads/images/2019-02-16/f0d12b6e036c514ecef17a3ae63ec11d.jpg"}
     */

    private int id;
    private String name;
    private String account;
    private String phone;
    private String mail;
    private int status;
    private String headimg;
    private String remark;
    private int company_id;
    private int department_id;
    private int position_id;
    private String company_name;
    private String created_at;
    private int is_monitoring;
    private DepartmentBean department;
    private PositionBean position;
    private CompanyBean company;
    private List<OfficeSealTpyeBean> office_seal_tpye;
    private List<OfficeSealBean> office_seal;
    private SysAppBean sys_app;
    private SysAuthBean sys_app_auth;
    private String is_daiqian;

    public String getIs_daiqian() {
        return is_daiqian;
    }

    public void setIs_daiqian(String is_daiqian) {
        this.is_daiqian = is_daiqian;
    }


    public SysAuthBean getAuthBean() {
        return sys_app_auth;
    }

    public void setAuthBean(SysAuthBean authBean) {
        this.sys_app_auth = authBean;
    }

    public static class SysAuthBean{
        private String app_auth;
        private String id;
        private String created_at;
        private String updated_at;

        public String getApp_auth() {
            return app_auth;
        }

        public void setApp_auth(String app_auth) {
            this.app_auth = app_auth;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

    public static class SysAppBean {

        /**
         * app_v : 1.0.0
         * app_label : 说明1
         * app_path :
         * app_type : 1
         * id : 0
         * created_at : 2019-02-16 21:04:00
         * updated_at : 2019-02-16 21:04:02
         */

        private String app_v;
        private String app_label;
        private String app_path;
        private int app_type;
        private int id;
        private String created_at;
        private String updated_at;

        public String getApp_v() {
            return app_v;
        }

        public void setApp_v(String app_v) {
            this.app_v = app_v;
        }

        public String getApp_label() {
            return app_label;
        }

        public void setApp_label(String app_label) {
            this.app_label = app_label;
        }

        public String getApp_path() {
            return app_path;
        }

        public void setApp_path(String app_path) {
            this.app_path = app_path;
        }

        public int getApp_type() {
            return app_type;
        }

        public void setApp_type(int app_type) {
            this.app_type = app_type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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


    public SysAppBean getSys_app() {
        return sys_app;
    }

    public void setSys_app(SysAppBean sys_app) {
        this.sys_app = sys_app;
    }

    public List<OfficeSealBean> getOffice_seal() {
        return office_seal;
    }

    public void setOffice_seal(List<OfficeSealBean> office_seal) {
        this.office_seal = office_seal;
    }

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getIs_monitoring() {
        return is_monitoring;
    }

    public void setIs_monitoring(int is_monitoring) {
        this.is_monitoring = is_monitoring;
    }

    public DepartmentBean getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentBean department) {
        this.department = department;
    }

    public PositionBean getPosition() {
        return position;
    }

    public void setPosition(PositionBean position) {
        this.position = position;
    }

    public CompanyBean getCompany() {
        return company;
    }

    public void setCompany(CompanyBean company) {
        this.company = company;
    }

    public List<OfficeSealTpyeBean> getOffice_seal_tpye() {
        return office_seal_tpye;
    }

    public void setOffice_seal_tpye(List<OfficeSealTpyeBean> office_seal_tpye) {
        this.office_seal_tpye = office_seal_tpye;
    }


    public static class DepartmentBean {
        /**
         * id : 4
         * name : 二秘办公室
         * parent_id : 1
         * sort : 1
         * level : 2
         * company_id : 1
         * created_at : 2018-12-20 19:29:54
         * updated_at : 2019-01-25 10:09:21
         */

        private int id;
        private String name;
        private int parent_id;
        private int sort;
        private int level;
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

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
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

    public static class PositionBean {
        /**
         * id : 5
         * name : 办公室副主任
         * level : 5
         * company_id : 1
         * created_at : 2019-01-08 14:47:16
         * updated_at : 2019-01-25 10:10:22
         */

        private int id;
        private String name;
        private int level;
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

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
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


    public static class OfficeSealBean {
        /**
         * id : 7
         * name : 我的印章1
         * office_seal_type_id : 1
         * path : http://oa.zycnb.net/admin/readFlie/40
         * company_id : 1
         * source_id : 40
         * user_id : 8
         * created_at : 2018-12-27 20:30:51
         * updated_at : 2018-12-27 20:30:51
         * office_seal_type : {"id":1,"name":"印章类型1","company_id":1,"created_at":"2018-12-23 16:14:43","updated_at":"2018-12-23 16:14:43"}
         * source : {"id":40,"name":"36711545903501_.pic_hd.jpg","user_id":8,"company_id":1,"path":"1/8/36711545903501_.pic_hd.jpg","created_at":"2018-12-27 20:30:47","updated_at":"2018-12-27 20:30:47"}
         */

        private int id;
        private String name;
        private int office_seal_type_id;
        private String path;
        private int company_id;
        private String source_id;
        private int user_id;
        private String created_at;
        private String updated_at;
        private OfficeSealTypeBean office_seal_type;
        private SourceBean source;

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

        public int getOffice_seal_type_id() {
            return office_seal_type_id;
        }

        public void setOffice_seal_type_id(int office_seal_type_id) {
            this.office_seal_type_id = office_seal_type_id;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public int getCompany_id() {
            return company_id;
        }

        public void setCompany_id(int company_id) {
            this.company_id = company_id;
        }

        public String getSource_id() {
            return source_id;
        }

        public void setSource_id(String source_id) {
            this.source_id = source_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
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

        public OfficeSealTypeBean getOffice_seal_type() {
            return office_seal_type;
        }

        public void setOffice_seal_type(OfficeSealTypeBean office_seal_type) {
            this.office_seal_type = office_seal_type;
        }

        public SourceBean getSource() {
            return source;
        }

        public void setSource(SourceBean source) {
            this.source = source;
        }

        public static class OfficeSealTypeBean {
            /**
             * id : 1
             * name : 印章类型1
             * company_id : 1
             * created_at : 2018-12-23 16:14:43
             * updated_at : 2018-12-23 16:14:43
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

        public static class SourceBean {
            /**
             * id : 40
             * name : 36711545903501_.pic_hd.jpg
             * user_id : 8
             * company_id : 1
             * path : 1/8/36711545903501_.pic_hd.jpg
             * created_at : 2018-12-27 20:30:47
             * updated_at : 2018-12-27 20:30:47
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
    }

    public static class CompanyBean {
        /**
         * id : 1
         * name : admin
         * account : admin
         * phone : asdada
         * mail : asdada
         * status : 1
         * headimg : http://oa.zycnb.net/uploads/images/2019-01-08/f0f17012fb5ac0b8771d823f7e4ff482.jpg
         * password : $2y$10$X6qEGidOZGEDYzbcrUHOhe1ISLfs1BveeadLcHCF5uJBqq/tgLD9i
         * remark : asdada
         * is_admin : 1
         * company_id : 1
         * department_id : 0
         * position_id : 0
         * company_name : 县政府
         * company_logo : asdada
         * remember_token : BmtVL9iGPQRAbIZFy4ire1MA5wtZSjfVKxSW29oZOT1nuQ9lWLdVGE0yXpFG
         * created_at : asdada
         * updated_at : 2019-02-16 17:07:00
         * number : 0
         * app_v : 1.0.0
         * app_label : 第一版本
         * app_type : 0
         * app_path : http://www.fly.com/uploads/images/2019-02-16/f0d12b6e036c514ecef17a3ae63ec11d.jpg
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
        private String company_logo;
        private String remember_token;
        private String created_at;
        private String updated_at;
        private int number;
        private String app_v;
        private String app_label;
        private int app_type;
        private String app_path;

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

        public String getCompany_logo() {
            return company_logo;
        }

        public void setCompany_logo(String company_logo) {
            this.company_logo = company_logo;
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

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getApp_v() {
            return app_v;
        }

        public void setApp_v(String app_v) {
            this.app_v = app_v;
        }

        public String getApp_label() {
            return app_label;
        }

        public void setApp_label(String app_label) {
            this.app_label = app_label;
        }

        public int getApp_type() {
            return app_type;
        }

        public void setApp_type(int app_type) {
            this.app_type = app_type;
        }

        public String getApp_path() {
            return app_path;
        }

        public void setApp_path(String app_path) {
            this.app_path = app_path;
        }
    }

    public static class OfficeSealTpyeBean {
        /**
         * id : 1
         * name : 印章类型1
         * company_id : 1
         * created_at : 2018-12-23 16:14:43
         * updated_at : 2018-12-23 16:14:43
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
