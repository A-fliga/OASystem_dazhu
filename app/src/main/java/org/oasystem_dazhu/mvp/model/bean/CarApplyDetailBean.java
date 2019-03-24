package org.oasystem_dazhu.mvp.model.bean;

import java.util.List;

/**
 * Created by www on 2019/3/23.
 */

public class CarApplyDetailBean {


    /**
     * id : 8
     * company_id : 1
     * user_id : 7
     * car_number : 1712
     * start_time : 0000-00-00 00:00:00
     * end_time : 0000-00-00 00:00:00
     * mileage : 22222
     * destination : 12312312
     * car_use_type_id : 3
     * created_at : 2019-03-09 21:38:44
     * updated_at : 2019-03-09 21:38:44
     * remark : asdad
     * status : 1
     * p_user_id : 39
     * user_ids : 39
     * user_ids_name : cs3
     * date : 0000-00-00 00:00:00-0000-00-00 00:00:00
     * status_name : 待审批
     * type_name : 特别用车
     * p_user_name : cs3
     * user : {"id":7,"name":"李志超","account":"李志超","phone":"asdad","mail":"asdad","status":1,"headimg":"http://oa.zycnb.net/uploads/images/2019-01-08/4d897a9b5b9a62d586483488ba7629c0.jpg","password":"$2y$10$4tgx7Y7bqNE.WM14EwHyAu1tIsKWDFYsCab6wiq3/3xF6dx9lCbD2","remark":"asdad","is_admin":0,"company_id":1,"department_id":11,"position_id":1,"company_name":"asdad","company_logo":"asdad","remember_token":"TzfcqGklTFM8O1EcAjtiuz9hSOf31AE7SEAvpsnSK8cMCrtW4xLZw6JIxL92","created_at":"2018-12-18 15:23:29","updated_at":"2019-02-20 14:49:39","number":1,"deleted_at":"asdad"}
     * car_use_type : {"id":3,"name":"特别用车","company_id":1,"created_at":"2019-03-08 20:34:59","updated_at":"2019-03-08 20:34:59"}
     * car_use_examine : [{"id":27,"company_id":1,"user_id":39,"car_use_id":8,"status":1,"remark":null,"created_at":"2019-03-09 21:38:44","updated_at":"2019-03-09 21:38:44","is_examine":1,"user":{"id":39,"name":"cs3","account":"cs3","phone":null,"mail":null,"status":1,"headimg":"http://www.fly.com/admin/img/head_img.jpeg","password":"$2y$10$V4nRSEylIbLE3mJEcIWooeUyHd/yM5BSqE5kdf69wJ5Wa1ij.32Fm","remark":null,"is_admin":0,"company_id":1,"department_id":1,"position_id":3,"company_name":null,"company_logo":null,"remember_token":"","created_at":"2019-03-02 17:02:25","updated_at":"2019-03-02 17:02:41","number":null,"deleted_at":null}}]
     * puser : {"id":39,"name":"cs3","account":"cs3","phone":"asdad","mail":"asdad","status":1,"headimg":"http://www.fly.com/admin/img/head_img.jpeg","password":"$2y$10$V4nRSEylIbLE3mJEcIWooeUyHd/yM5BSqE5kdf69wJ5Wa1ij.32Fm","remark":"asdad","is_admin":0,"company_id":1,"department_id":1,"position_id":3,"company_name":"asdad","company_logo":"asdad","remember_token":"","created_at":"2019-03-02 17:02:25","updated_at":"2019-03-02 17:02:41","number":"asdad","deleted_at":"asdad"}
     * car_use_examine_one : {"id":27,"company_id":1,"user_id":39,"car_use_id":8,"status":1,"remark":"adas","created_at":"2019-03-09 21:38:44","updated_at":"2019-03-09 21:38:44","is_examine":1}
     */

    private String id;
    private String company_id;
    private String user_id;
    private String car_number;
    private String start_time;
    private String end_time;
    private String mileage;
    private String destination;
    private String car_use_type_id;
    private String created_at;
    private String updated_at;
    private String remark;
    private String status;
    private String p_user_id;
    private String user_ids;
    private String user_ids_name;
    private String date;
    private String status_name;
    private String type_name;
    private String p_user_name;
    private UserBean user;
    private CarUseTypeBean car_use_type;
    private PuserBean puser;
    private CarUseExamineOneBean car_use_examine_one;
    private List<CarUseExamineBean> car_use_examine;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCar_number() {
        return car_number;
    }

    public void setCar_number(String car_number) {
        this.car_number = car_number;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getCar_use_type_id() {
        return car_use_type_id;
    }

    public void setCar_use_type_id(String car_use_type_id) {
        this.car_use_type_id = car_use_type_id;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getP_user_id() {
        return p_user_id;
    }

    public void setP_user_id(String p_user_id) {
        this.p_user_id = p_user_id;
    }

    public String getUser_ids() {
        return user_ids;
    }

    public void setUser_ids(String user_ids) {
        this.user_ids = user_ids;
    }

    public String getUser_ids_name() {
        return user_ids_name;
    }

    public void setUser_ids_name(String user_ids_name) {
        this.user_ids_name = user_ids_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getP_user_name() {
        return p_user_name;
    }

    public void setP_user_name(String p_user_name) {
        this.p_user_name = p_user_name;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public CarUseTypeBean getCar_use_type() {
        return car_use_type;
    }

    public void setCar_use_type(CarUseTypeBean car_use_type) {
        this.car_use_type = car_use_type;
    }

    public PuserBean getPuser() {
        return puser;
    }

    public void setPuser(PuserBean puser) {
        this.puser = puser;
    }

    public CarUseExamineOneBean getCar_use_examine_one() {
        return car_use_examine_one;
    }

    public void setCar_use_examine_one(CarUseExamineOneBean car_use_examine_one) {
        this.car_use_examine_one = car_use_examine_one;
    }

    public List<CarUseExamineBean> getCar_use_examine() {
        return car_use_examine;
    }

    public void setCar_use_examine(List<CarUseExamineBean> car_use_examine) {
        this.car_use_examine = car_use_examine;
    }

    public static class UserBean {
        /**
         * id : 7
         * name : 李志超
         * account : 李志超
         * phone : asdad
         * mail : asdad
         * status : 1
         * headimg : http://oa.zycnb.net/uploads/images/2019-01-08/4d897a9b5b9a62d586483488ba7629c0.jpg
         * password : $2y$10$4tgx7Y7bqNE.WM14EwHyAu1tIsKWDFYsCab6wiq3/3xF6dx9lCbD2
         * remark : asdad
         * is_admin : 0
         * company_id : 1
         * department_id : 11
         * position_id : 1
         * company_name : asdad
         * company_logo : asdad
         * remember_token : TzfcqGklTFM8O1EcAjtiuz9hSOf31AE7SEAvpsnSK8cMCrtW4xLZw6JIxL92
         * created_at : 2018-12-18 15:23:29
         * updated_at : 2019-02-20 14:49:39
         * number : 1
         * deleted_at : asdad
         */

        private String id;
        private String name;
        private String account;
        private String phone;
        private String mail;
        private String status;
        private String headimg;
        private String password;
        private String remark;
        private String is_admin;
        private String company_id;
        private String department_id;
        private String position_id;
        private String company_name;
        private String company_logo;
        private String remember_token;
        private String created_at;
        private String updated_at;
        private String number;
        private String deleted_at;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
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

        public String getIs_admin() {
            return is_admin;
        }

        public void setIs_admin(String is_admin) {
            this.is_admin = is_admin;
        }

        public String getCompany_id() {
            return company_id;
        }

        public void setCompany_id(String company_id) {
            this.company_id = company_id;
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

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getDeleted_at() {
            return deleted_at;
        }

        public void setDeleted_at(String deleted_at) {
            this.deleted_at = deleted_at;
        }
    }

    public static class CarUseTypeBean {
        /**
         * id : 3
         * name : 特别用车
         * company_id : 1
         * created_at : 2019-03-08 20:34:59
         * updated_at : 2019-03-08 20:34:59
         */

        private String id;
        private String name;
        private String company_id;
        private String created_at;
        private String updated_at;

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
    }

    public static class PuserBean {
        /**
         * id : 39
         * name : cs3
         * account : cs3
         * phone : asdad
         * mail : asdad
         * status : 1
         * headimg : http://www.fly.com/admin/img/head_img.jpeg
         * password : $2y$10$V4nRSEylIbLE3mJEcIWooeUyHd/yM5BSqE5kdf69wJ5Wa1ij.32Fm
         * remark : asdad
         * is_admin : 0
         * company_id : 1
         * department_id : 1
         * position_id : 3
         * company_name : asdad
         * company_logo : asdad
         * remember_token : 
         * created_at : 2019-03-02 17:02:25
         * updated_at : 2019-03-02 17:02:41
         * number : asdad
         * deleted_at : asdad
         */

        private String id;
        private String name;
        private String account;
        private String phone;
        private String mail;
        private String status;
        private String headimg;
        private String password;
        private String remark;
        private String is_admin;
        private String company_id;
        private String department_id;
        private String position_id;
        private String company_name;
        private String company_logo;
        private String remember_token;
        private String created_at;
        private String updated_at;
        private String number;
        private String deleted_at;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
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

        public String getIs_admin() {
            return is_admin;
        }

        public void setIs_admin(String is_admin) {
            this.is_admin = is_admin;
        }

        public String getCompany_id() {
            return company_id;
        }

        public void setCompany_id(String company_id) {
            this.company_id = company_id;
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

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getDeleted_at() {
            return deleted_at;
        }

        public void setDeleted_at(String deleted_at) {
            this.deleted_at = deleted_at;
        }
    }

    public static class CarUseExamineOneBean {
        /**
         * id : 27
         * company_id : 1
         * user_id : 39
         * car_use_id : 8
         * status : 1
         * remark : adas
         * created_at : 2019-03-09 21:38:44
         * updated_at : 2019-03-09 21:38:44
         * is_examine : 1
         */

        private String id;
        private String company_id;
        private String user_id;
        private String car_use_id;
        private String status;
        private String remark;
        private String created_at;
        private String updated_at;
        private String is_examine;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCompany_id() {
            return company_id;
        }

        public void setCompany_id(String company_id) {
            this.company_id = company_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getCar_use_id() {
            return car_use_id;
        }

        public void setCar_use_id(String car_use_id) {
            this.car_use_id = car_use_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
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

        public String getIs_examine() {
            return is_examine;
        }

        public void setIs_examine(String is_examine) {
            this.is_examine = is_examine;
        }
    }

    public static class CarUseExamineBean {
        /**
         * id : 27
         * company_id : 1
         * user_id : 39
         * car_use_id : 8
         * status : 1
         * remark : null
         * created_at : 2019-03-09 21:38:44
         * updated_at : 2019-03-09 21:38:44
         * is_examine : 1
         * user : {"id":39,"name":"cs3","account":"cs3","phone":null,"mail":null,"status":1,"headimg":"http://www.fly.com/admin/img/head_img.jpeg","password":"$2y$10$V4nRSEylIbLE3mJEcIWooeUyHd/yM5BSqE5kdf69wJ5Wa1ij.32Fm","remark":null,"is_admin":0,"company_id":1,"department_id":1,"position_id":3,"company_name":null,"company_logo":null,"remember_token":"","created_at":"2019-03-02 17:02:25","updated_at":"2019-03-02 17:02:41","number":null,"deleted_at":null}
         */

        private String id;
        private String company_id;
        private String user_id;
        private String car_use_id;
        private String status;
        private String remark;
        private String created_at;
        private String updated_at;
        private String is_examine;
        private UserBeanX user;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCompany_id() {
            return company_id;
        }

        public void setCompany_id(String company_id) {
            this.company_id = company_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getCar_use_id() {
            return car_use_id;
        }

        public void setCar_use_id(String car_use_id) {
            this.car_use_id = car_use_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
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

        public String getIs_examine() {
            return is_examine;
        }

        public void setIs_examine(String is_examine) {
            this.is_examine = is_examine;
        }

        public UserBeanX getUser() {
            return user;
        }

        public void setUser(UserBeanX user) {
            this.user = user;
        }

        public static class UserBeanX {
            /**
             * id : 39
             * name : cs3
             * account : cs3
             * phone : null
             * mail : null
             * status : 1
             * headimg : http://www.fly.com/admin/img/head_img.jpeg
             * password : $2y$10$V4nRSEylIbLE3mJEcIWooeUyHd/yM5BSqE5kdf69wJ5Wa1ij.32Fm
             * remark : null
             * is_admin : 0
             * company_id : 1
             * department_id : 1
             * position_id : 3
             * company_name : null
             * company_logo : null
             * remember_token : 
             * created_at : 2019-03-02 17:02:25
             * updated_at : 2019-03-02 17:02:41
             * number : null
             * deleted_at : null
             */

            private String id;
            private String name;
            private String account;
            private String phone;
            private String mail;
            private String status;
            private String headimg;
            private String password;
            private String remark;
            private String is_admin;
            private String company_id;
            private String department_id;
            private String position_id;
            private String company_name;
            private String company_logo;
            private String remember_token;
            private String created_at;
            private String updated_at;
            private String number;
            private String deleted_at;

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

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
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

            public String getIs_admin() {
                return is_admin;
            }

            public void setIs_admin(String is_admin) {
                this.is_admin = is_admin;
            }

            public String getCompany_id() {
                return company_id;
            }

            public void setCompany_id(String company_id) {
                this.company_id = company_id;
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

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getDeleted_at() {
                return deleted_at;
            }

            public void setDeleted_at(String deleted_at) {
                this.deleted_at = deleted_at;
            }
        }
    }
}
