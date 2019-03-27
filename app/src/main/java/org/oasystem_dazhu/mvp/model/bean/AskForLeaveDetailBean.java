package org.oasystem_dazhu.mvp.model.bean;

import java.util.List;

/**
 * Created by www on 2019/3/26.
 */

public class AskForLeaveDetailBean {

    /**
     * id : 8
     * user_id : 39
     * attendance_type_id : 2
     * start_time : 2019-03-13 00:00:00
     * end_time : 2019-03-14 00:00:00
     * remark : 1212
     * status : 1
     * created_at : 2019-03-13 21:00:36
     * updated_at : 2019-03-13 21:00:36
     * company_id : 1
     * user_ids : 7
     * user_ids_name : 李志超
     * date : 2019-03-13 00:00:00-2019-03-14 00:00:00
     * status_name : 待审批
     * type_name : 年假
     * user_name : cs3
     * user_department_name : 大竹县政府
     * user : {"id":39,"name":"cs3","account":"cs3","phone":null,"mail":null,"status":1,"headimg":"http://www.fly.com/admin/img/head_img.jpeg","password":"$2y$10$V4nRSEylIbLE3mJEcIWooeUyHd/yM5BSqE5kdf69wJ5Wa1ij.32Fm","remark":null,"is_admin":0,"company_id":1,"department_id":1,"position_id":3,"company_name":null,"company_logo":null,"remember_token":"bKWdvzrKXvLjT2hKoV2yZxjSK7rTNLmmu5McuqAVjUWpvXmQscmUN7OaNNLg","created_at":"2019-03-02 17:02:25","updated_at":"2019-03-02 17:02:41","number":null,"deleted_at":null,"department":{"id":1,"name":"大竹县政府","parent_id":0,"sort":1,"level":1,"company_id":1,"created_at":"2018-12-20 19:21:07","updated_at":"2019-01-25 10:08:54"},"position":{"id":3,"name":"副县长","level":3,"company_id":1,"created_at":"2019-01-08 14:46:52","updated_at":"2019-01-08 14:46:52"}}
     * attendance_type : {"id":2,"name":"年假","company_id":1,"created_at":"2019-03-11 21:14:28","updated_at":"2019-03-11 21:14:28"}
     * attendance_examine : [{"id":12,"company_id":1,"user_id":7,"attendance_id":8,"status":1,"remark":null,"created_at":"2019-03-13 21:00:36","updated_at":"2019-03-13 21:00:36","is_examine":1,"user":{"id":7,"name":"李志超","account":"李志超","phone":null,"mail":null,"status":1,"headimg":"http://oa.zycnb.net/uploads/images/2019-01-08/4d897a9b5b9a62d586483488ba7629c0.jpg","password":"$2y$10$4tgx7Y7bqNE.WM14EwHyAu1tIsKWDFYsCab6wiq3/3xF6dx9lCbD2","remark":null,"is_admin":0,"company_id":1,"department_id":11,"position_id":1,"company_name":null,"company_logo":null,"remember_token":"L4mCjvLSttdtcUlmQnC0uiMPQxy6SIbDNMFiZt2WCpMrOCYJCg09O4woezA2","created_at":"2018-12-18 15:23:29","updated_at":"2019-02-20 14:49:39","number":1,"deleted_at":null}}]
     */

    private String id;
    private String user_id;
    private String attendance_type_id;
    private String start_time;
    private String end_time;
    private String remark;
    private String status;
    private String created_at;
    private String updated_at;
    private String company_id;
    private String user_ids;
    private String user_ids_name;
    private String date;
    private String status_name;
    private String type_name;
    private String user_name;
    private String user_department_name;
    private UserBean user;
    private AttendanceTypeBean attendance_type;
    private List<AttendanceExamineBean> attendance_examine;

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

    public String getAttendance_type_id() {
        return attendance_type_id;
    }

    public void setAttendance_type_id(String attendance_type_id) {
        this.attendance_type_id = attendance_type_id;
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

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_department_name() {
        return user_department_name;
    }

    public void setUser_department_name(String user_department_name) {
        this.user_department_name = user_department_name;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public AttendanceTypeBean getAttendance_type() {
        return attendance_type;
    }

    public void setAttendance_type(AttendanceTypeBean attendance_type) {
        this.attendance_type = attendance_type;
    }

    public List<AttendanceExamineBean> getAttendance_examine() {
        return attendance_examine;
    }

    public void setAttendance_examine(List<AttendanceExamineBean> attendance_examine) {
        this.attendance_examine = attendance_examine;
    }

    public static class UserBean {
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
         * remember_token : bKWdvzrKXvLjT2hKoV2yZxjSK7rTNLmmu5McuqAVjUWpvXmQscmUN7OaNNLg
         * created_at : 2019-03-02 17:02:25
         * updated_at : 2019-03-02 17:02:41
         * number : null
         * deleted_at : null
         * department : {"id":1,"name":"大竹县政府","parent_id":0,"sort":1,"level":1,"company_id":1,"created_at":"2018-12-20 19:21:07","updated_at":"2019-01-25 10:08:54"}
         * position : {"id":3,"name":"副县长","level":3,"company_id":1,"created_at":"2019-01-08 14:46:52","updated_at":"2019-01-08 14:46:52"}
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
        private DepartmentBean department;
        private PositionBean position;

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

        public static class DepartmentBean {
            /**
             * id : 1
             * name : 大竹县政府
             * parent_id : 0
             * sort : 1
             * level : 1
             * company_id : 1
             * created_at : 2018-12-20 19:21:07
             * updated_at : 2019-01-25 10:08:54
             */

            private String id;
            private String name;
            private String parent_id;
            private String sort;
            private String level;
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

            public String getParent_id() {
                return parent_id;
            }

            public void setParent_id(String parent_id) {
                this.parent_id = parent_id;
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
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
        }

        public static class PositionBean {
            /**
             * id : 3
             * name : 副县长
             * level : 3
             * company_id : 1
             * created_at : 2019-01-08 14:46:52
             * updated_at : 2019-01-08 14:46:52
             */

            private String id;
            private String name;
            private String level;
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
        }
    }

    public static class AttendanceTypeBean {
        /**
         * id : 2
         * name : 年假
         * company_id : 1
         * created_at : 2019-03-11 21:14:28
         * updated_at : 2019-03-11 21:14:28
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

    public static class AttendanceExamineBean {
        /**
         * id : 12
         * company_id : 1
         * user_id : 7
         * attendance_id : 8
         * status : 1
         * remark : null
         * created_at : 2019-03-13 21:00:36
         * updated_at : 2019-03-13 21:00:36
         * is_examine : 1
         * user : {"id":7,"name":"李志超","account":"李志超","phone":null,"mail":null,"status":1,"headimg":"http://oa.zycnb.net/uploads/images/2019-01-08/4d897a9b5b9a62d586483488ba7629c0.jpg","password":"$2y$10$4tgx7Y7bqNE.WM14EwHyAu1tIsKWDFYsCab6wiq3/3xF6dx9lCbD2","remark":null,"is_admin":0,"company_id":1,"department_id":11,"position_id":1,"company_name":null,"company_logo":null,"remember_token":"L4mCjvLSttdtcUlmQnC0uiMPQxy6SIbDNMFiZt2WCpMrOCYJCg09O4woezA2","created_at":"2018-12-18 15:23:29","updated_at":"2019-02-20 14:49:39","number":1,"deleted_at":null}
         */

        private String id;
        private String company_id;
        private String user_id;
        private String attendance_id;
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

        public String getAttendance_id() {
            return attendance_id;
        }

        public void setAttendance_id(String attendance_id) {
            this.attendance_id = attendance_id;
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
             * id : 7
             * name : 李志超
             * account : 李志超
             * phone : null
             * mail : null
             * status : 1
             * headimg : http://oa.zycnb.net/uploads/images/2019-01-08/4d897a9b5b9a62d586483488ba7629c0.jpg
             * password : $2y$10$4tgx7Y7bqNE.WM14EwHyAu1tIsKWDFYsCab6wiq3/3xF6dx9lCbD2
             * remark : null
             * is_admin : 0
             * company_id : 1
             * department_id : 11
             * position_id : 1
             * company_name : null
             * company_logo : null
             * remember_token : L4mCjvLSttdtcUlmQnC0uiMPQxy6SIbDNMFiZt2WCpMrOCYJCg09O4woezA2
             * created_at : 2018-12-18 15:23:29
             * updated_at : 2019-02-20 14:49:39
             * number : 1
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
