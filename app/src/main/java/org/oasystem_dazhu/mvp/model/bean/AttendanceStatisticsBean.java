package org.oasystem_dazhu.mvp.model.bean;

import java.util.List;

/**
 * Created by www on 2019/3/25.
 */

public class AttendanceStatisticsBean {

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
     * department_name : 大竹县政府
     * attendance_record_count : 2
     * chidao_count : 0
     * zaotui_count : 0
     * queka_count : 77
     * field_1 : 2
     * department : {"id":1,"name":"大竹县政府","parent_id":0,"sort":1,"level":1,"company_id":1,"created_at":"2018-12-20 19:21:07","updated_at":"2019-01-25 10:08:54"}
     * attendance_record : [{"id":8,"user_id":39,"start_time":"2019-03-13 08:05:52","end_time":"2019-03-13 22:05:52","start_lat":"12.2323000","start_lng":"21.2222000","start_type":2,"created_at":"2019-03-13 22:29:19","updated_at":"2019-03-13 22:32:59","company_id":1,"end_lng":"21.2222000","end_lat":"12.2323000","end_type":2,"date":"2019-03-13"},{"id":9,"user_id":39,"start_time":"2019-03-13 08:05:52","end_time":"2019-03-13 22:05:52","start_lat":"12.2323000","start_lng":"21.2222000","start_type":2,"created_at":"2019-03-13 22:29:19","updated_at":"2019-03-13 22:32:59","company_id":1,"end_lng":"21.2222000","end_lat":"12.2323000","end_type":2,"date":"2019-03-14"}]
     * authenticate : [{"id":2,"user_id":39,"attendance_type_id":1,"start_time":"2019-03-11 21:58:07","end_time":"2019-03-12 21:25:21","remark":"2222","status":2,"created_at":"2019-03-11 21:25:07","updated_at":"2019-03-11 21:58:46","company_id":1,"attendance_type":{"id":1,"name":"事假","company_id":1,"created_at":"2019-03-11 21:14:02","updated_at":"2019-03-11 21:14:42"}},{"id":6,"user_id":39,"attendance_type_id":1,"start_time":"2019-03-12 20:29:48","end_time":"2019-03-13 00:00:00","remark":"das","status":2,"created_at":"2019-03-12 20:30:17","updated_at":"2019-03-12 20:30:28","company_id":1,"attendance_type":{"id":1,"name":"事假","company_id":1,"created_at":"2019-03-11 21:14:02","updated_at":"2019-03-11 21:14:42"}}]
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
    private String department_name;
    private String attendance_record_count;
    private String chidao_count;
    private String zaotui_count;
    private String queka_count;
    private String field_1;
    private DepartmentBean department;
    private List<AttendanceRecordBean> attendance_record;
    private List<AuthenticateBean> authenticate;

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

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getAttendance_record_count() {
        return attendance_record_count;
    }

    public void setAttendance_record_count(String attendance_record_count) {
        this.attendance_record_count = attendance_record_count;
    }

    public String getChidao_count() {
        return chidao_count;
    }

    public void setChidao_count(String chidao_count) {
        this.chidao_count = chidao_count;
    }

    public String getZaotui_count() {
        return zaotui_count;
    }

    public void setZaotui_count(String zaotui_count) {
        this.zaotui_count = zaotui_count;
    }

    public String getQueka_count() {
        return queka_count;
    }

    public void setQueka_count(String queka_count) {
        this.queka_count = queka_count;
    }

    public String getField_1() {
        return field_1;
    }

    public void setField_1(String field_1) {
        this.field_1 = field_1;
    }

    public DepartmentBean getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentBean department) {
        this.department = department;
    }

    public List<AttendanceRecordBean> getAttendance_record() {
        return attendance_record;
    }

    public void setAttendance_record(List<AttendanceRecordBean> attendance_record) {
        this.attendance_record = attendance_record;
    }

    public List<AuthenticateBean> getAuthenticate() {
        return authenticate;
    }

    public void setAuthenticate(List<AuthenticateBean> authenticate) {
        this.authenticate = authenticate;
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

    public static class AttendanceRecordBean {
        /**
         * id : 8
         * user_id : 39
         * start_time : 2019-03-13 08:05:52
         * end_time : 2019-03-13 22:05:52
         * start_lat : 12.2323000
         * start_lng : 21.2222000
         * start_type : 2
         * created_at : 2019-03-13 22:29:19
         * updated_at : 2019-03-13 22:32:59
         * company_id : 1
         * end_lng : 21.2222000
         * end_lat : 12.2323000
         * end_type : 2
         * date : 2019-03-13
         */

        private String id;
        private String user_id;
        private String start_time;
        private String end_time;
        private String start_lat;
        private String start_lng;
        private String start_type;
        private String created_at;
        private String updated_at;
        private String company_id;
        private String end_lng;
        private String end_lat;
        private String end_type;
        private String date;

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

        public String getStart_lat() {
            return start_lat;
        }

        public void setStart_lat(String start_lat) {
            this.start_lat = start_lat;
        }

        public String getStart_lng() {
            return start_lng;
        }

        public void setStart_lng(String start_lng) {
            this.start_lng = start_lng;
        }

        public String getStart_type() {
            return start_type;
        }

        public void setStart_type(String start_type) {
            this.start_type = start_type;
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

        public String getEnd_lng() {
            return end_lng;
        }

        public void setEnd_lng(String end_lng) {
            this.end_lng = end_lng;
        }

        public String getEnd_lat() {
            return end_lat;
        }

        public void setEnd_lat(String end_lat) {
            this.end_lat = end_lat;
        }

        public String getEnd_type() {
            return end_type;
        }

        public void setEnd_type(String end_type) {
            this.end_type = end_type;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }

    public static class AuthenticateBean {
        /**
         * id : 2
         * user_id : 39
         * attendance_type_id : 1
         * start_time : 2019-03-11 21:58:07
         * end_time : 2019-03-12 21:25:21
         * remark : 2222
         * status : 2
         * created_at : 2019-03-11 21:25:07
         * updated_at : 2019-03-11 21:58:46
         * company_id : 1
         * attendance_type : {"id":1,"name":"事假","company_id":1,"created_at":"2019-03-11 21:14:02","updated_at":"2019-03-11 21:14:42"}
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
        private AttendanceTypeBean attendance_type;

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

        public AttendanceTypeBean getAttendance_type() {
            return attendance_type;
        }

        public void setAttendance_type(AttendanceTypeBean attendance_type) {
            this.attendance_type = attendance_type;
        }

        public static class AttendanceTypeBean {
            /**
             * id : 1
             * name : 事假
             * company_id : 1
             * created_at : 2019-03-11 21:14:02
             * updated_at : 2019-03-11 21:14:42
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
    }
}
