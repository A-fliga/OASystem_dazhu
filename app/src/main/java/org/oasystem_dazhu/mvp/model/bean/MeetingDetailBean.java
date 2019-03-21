package org.oasystem_dazhu.mvp.model.bean;

import java.util.List;

/**
 * Created by www on 2019/3/20.
 */

public class MeetingDetailBean {


    /**
     * id : 35
     * name : 关于xxx的讨论会议
     * company_id : 1
     * conference_type_id : 1
     * created_at : 2019-03-17 10:50:09
     * updated_at : 2019-03-17 10:50:09
     * status : 1
     * compere_name : linkai
     * age_name : 林楷
     * linkman : 林楷
     * contact : 123456789
     * type : 1
     * start_time : 2019-03-17 11:00:00
     * end_time : 2019-03-17 02:00:00
     * user_ids : 11,12,13,14,7,10,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39
     * user_ids_name : 王英,任黎,刘保华,宁小礼,李志超,吴继承,刘烨,李绍华,刘高鹏,温国林,徐成,华英,楚昌洪,杜识宝,胡伟,王华勇,谢隽峰,莫林川,陈建,高超,张杰,胡波,李坤鹏,罗喆,蒋东莉,综合室,副主任1,副主任2,副主任3,副主任4,cs3
     * conference_room : 1,1,1,1
     * conference_room_names : 1-1
     * date : 2019-03-17 11:00:00-2019-03-17 02:00:00
     * now_status : 3
     * now_status_name : 已结束
     * conference_type_name : 会议类型1
     * vStr : [{"conference_room_id":1,"date":"2019-03-17","hour":11,"key":"2c72f701c2a6f8483c7e4f27532c1160"},{"conference_room_id":1,"date":"2019-03-17","hour":12,"key":"4cb1890f517f4bc0be969026252a6939"},{"conference_room_id":1,"date":"2019-03-17","hour":13,"key":"01dd496407a5342e30024fd5596a0870"},{"conference_room_id":1,"date":"2019-03-17","hour":14,"key":"9429d9a5ab52d1dd83fb89181c26146b"}]
     * conference_user : [{"id":76,"user_id":11,"conference_id":35,"created_at":"2019-03-17 10:50:09","updated_at":"2019-03-17 10:50:09","status":0,"remark":null,"company_id":1,"user":{"id":11,"name":"王英","account":"王英","phone":null,"mail":"admin","status":1,"headimg":"http://oa.zycnb.net/admin/img/head_img.jpeg","password":"$2y$10$8vQEU0tm9jIugGW7Y8AwseD9zZJon/btql3cfR1oZ3/xnbibFss2q","remark":null,"is_admin":0,"company_id":1,"department_id":11,"position_id":3,"company_name":null,"company_logo":null,"remember_token":"","created_at":"2019-02-20 14:50:47","updated_at":"2019-02-20 14:52:37","number":2,"deleted_at":null}},{"id":77,"user_id":12,"conference_id":35,"created_at":"2019-03-17 10:50:09","updated_at":"2019-03-17 10:50:09","status":0,"remark":null,"company_id":1,"user":{"id":12,"name":"任黎","account":"任黎","phone":null,"mail":"admin","status":1,"headimg":"http://oa.zycnb.net/admin/img/head_img.jpeg","password":"$2y$10$wYN1zaOP0nMNCrFHKOdEluHiBif07VeoZdojj3y8Qnw/Vz7icalOa","remark":null,"is_admin":0,"company_id":1,"department_id":11,"position_id":3,"company_name":null,"company_logo":null,"remember_token":"","created_at":"2019-02-20 14:51:31","updated_at":"2019-02-20 14:52:55","number":3,"deleted_at":null}},{"id":78,"user_id":13,"conference_id":35,"created_at":"2019-03-17 10:50:09","updated_at":"2019-03-17 10:50:09","status":0,"remark":null,"company_id":1,"user":{"id":13,"name":"刘保华","account":"刘保华","phone":null,"mail":null,"status":1,"headimg":"http://oa.zycnb.net/admin/img/head_img.jpeg","password":"$2y$10$cT0tbueJ50wr/FNHmwpD4umm1skSMgTZpt0pL6.o2BCiLNTtt40yq","remark":null,"is_admin":0,"company_id":1,"department_id":11,"position_id":3,"company_name":null,"company_logo":null,"remember_token":"","created_at":"2019-02-20 14:52:10","updated_at":"2019-02-20 14:52:47","number":4,"deleted_at":null}},{"id":79,"user_id":14,"conference_id":35,"created_at":"2019-03-17 10:50:09","updated_at":"2019-03-17 10:50:09","status":0,"remark":null,"company_id":1,"user":{"id":14,"name":"宁小礼","account":"宁小礼","phone":null,"mail":null,"status":1,"headimg":"http://oa.zycnb.net/admin/img/head_img.jpeg","password":"$2y$10$ZacuQA7HJvuFypOduNiJJugzkbowkPJ25Vf.mYgDHTR6FUgAQo7M.","remark":null,"is_admin":0,"company_id":1,"department_id":11,"position_id":3,"company_name":null,"company_logo":null,"remember_token":"","created_at":"2019-02-20 14:53:20","updated_at":"2019-02-20 14:53:20","number":5,"deleted_at":null}},{"id":80,"user_id":7,"conference_id":35,"created_at":"2019-03-17 10:50:09","updated_at":"2019-03-17 10:50:09","status":0,"remark":null,"company_id":1,"user":{"id":7,"name":"李志超","account":"李志超","phone":null,"mail":null,"status":1,"headimg":"http://oa.zycnb.net/uploads/images/2019-01-08/4d897a9b5b9a62d586483488ba7629c0.jpg","password":"$2y$10$4tgx7Y7bqNE.WM14EwHyAu1tIsKWDFYsCab6wiq3/3xF6dx9lCbD2","remark":null,"is_admin":0,"company_id":1,"department_id":11,"position_id":1,"company_name":null,"company_logo":null,"remember_token":"L4mCjvLSttdtcUlmQnC0uiMPQxy6SIbDNMFiZt2WCpMrOCYJCg09O4woezA2","created_at":"2018-12-18 15:23:29","updated_at":"2019-02-20 14:49:39","number":1,"deleted_at":null}},{"id":81,"user_id":10,"conference_id":35,"created_at":"2019-03-17 10:50:09","updated_at":"2019-03-17 10:50:09","status":0,"remark":null,"company_id":1,"user":{"id":10,"name":"吴继承","account":"吴继承","phone":null,"mail":"admin","status":1,"headimg":"http://oa.zycnb.net/uploads/images/2019-01-08/029c60c7993de4c644885c641f18f30f.jpg","password":"$2y$10$fQiUEhrxhgm/FhxiMLhF3.IxpJVaorUZq/QZlpzOytNQ/Zszg9q2u","remark":null,"is_admin":0,"company_id":1,"department_id":11,"position_id":3,"company_name":null,"company_logo":null,"remember_token":"Wcst4s8WylDQ1Xe2WigfLGOIKeGSqvS4rBOCz1BVuqNutmiFFx4bCHgcGPJk","created_at":"2019-01-08 14:52:57","updated_at":"2019-02-20 14:51:49","number":1,"deleted_at":null}},{"id":82,"user_id":15,"conference_id":35,"created_at":"2019-03-17 10:50:09","updated_at":"2019-03-17 10:50:09","status":0,"remark":null,"company_id":1,"user":{"id":15,"name":"刘烨","account":"刘烨","phone":null,"mail":null,"status":1,"headimg":"http://oa.zycnb.net/admin/img/head_img.jpeg","password":"$2y$10$MkleCvVOUylgdnoBoILT1.V7pKWWqb0q9dH7Zgd.3xHqCRVbJI.du","remark":null,"is_admin":0,"company_id":1,"department_id":11,"position_id":3,"company_name":null,"company_logo":null,"remember_token":"","created_at":"2019-02-20 14:54:21","updated_at":"2019-02-20 14:54:30","number":6,"deleted_at":null}},{"id":83,"user_id":16,"conference_id":35,"created_at":"2019-03-17 10:50:09","updated_at":"2019-03-17 10:50:09","status":0,"remark":null,"company_id":1,"user":{"id":16,"name":"李绍华","account":"李绍华","phone":null,"mail":null,"status":1,"headimg":"http://oa.zycnb.net/admin/img/head_img.jpeg","password":"$2y$10$ZdBkPZiTekKBfIEeLFgdL.z1ZBd3BO8fFFG1.F3LYA8Y0k0LW6rgC","remark":null,"is_admin":0,"company_id":1,"department_id":11,"position_id":3,"company_name":null,"company_logo":null,"remember_token":"","created_at":"2019-02-20 14:55:13","updated_at":"2019-02-20 14:55:13","number":7,"deleted_at":null}},{"id":84,"user_id":17,"conference_id":35,"created_at":"2019-03-17 10:50:09","updated_at":"2019-03-17 10:50:09","status":0,"remark":null,"company_id":1,"user":{"id":17,"name":"刘高鹏","account":"刘高鹏","phone":null,"mail":null,"status":1,"headimg":"http://oa.zycnb.net/admin/img/head_img.jpeg","password":"$2y$10$pI5DFqkjvHBlH4CYxC4smuc9C/3160YPcKBJGWnRUVb1g1tEsuv72","remark":null,"is_admin":0,"company_id":1,"department_id":4,"position_id":4,"company_name":null,"company_logo":null,"remember_token":"","created_at":"2019-02-20 14:55:43","updated_at":"2019-02-20 14:55:43","number":null,"deleted_at":null}},{"id":85,"user_id":18,"conference_id":35,"created_at":"2019-03-17 10:50:09","updated_at":"2019-03-17 10:50:09","status":0,"remark":null,"company_id":1,"user":{"id":18,"name":"温国林","account":"温国林","phone":null,"mail":null,"status":1,"headimg":"http://oa.zycnb.net/admin/img/head_img.jpeg","password":"$2y$10$uoJA1vREfxE9HxmKHrgVk.sB0mmgiTf.nrq3JsAxk2gLMMUg/hF5a","remark":null,"is_admin":0,"company_id":1,"department_id":4,"position_id":5,"company_name":null,"company_logo":null,"remember_token":"","created_at":"2019-02-20 14:56:08","updated_at":"2019-02-20 14:56:08","number":1,"deleted_at":null}},{"id":86,"user_id":19,"conference_id":35,"created_at":"2019-03-17 10:50:09","updated_at":"2019-03-17 10:50:09","status":0,"remark":null,"company_id":1,"user":{"id":19,"name":"徐成","account":"徐成","phone":null,"mail":null,"status":1,"headimg":"http://oa.zycnb.net/admin/img/head_img.jpeg","password":"$2y$10$Kq64OS8Ojme8qFXFpGWUxOVwhSGS9Ekg/mxtNcmU3No5QcVsmUV02","remark":null,"is_admin":0,"company_id":1,"department_id":4,"position_id":5,"company_name":null,"company_logo":null,"remember_token":"","created_at":"2019-02-20 14:56:36","updated_at":"2019-02-20 14:56:36","number":2,"deleted_at":null}},{"id":87,"user_id":20,"conference_id":35,"created_at":"2019-03-17 10:50:09","updated_at":"2019-03-17 10:50:09","status":0,"remark":null,"company_id":1,"user":{"id":20,"name":"华英","account":"华英","phone":null,"mail":null,"status":1,"headimg":"http://oa.zycnb.net/admin/img/head_img.jpeg","password":"$2y$10$yJr4zwl0f34bx4vcJEjTwuf9yFgWDsc9aDfF2DavM3IW3qZNZJIWm","remark":null,"is_admin":0,"company_id":1,"department_id":4,"position_id":3,"company_name":null,"company_logo":null,"remember_token":"","created_at":"2019-02-20 14:56:58","updated_at":"2019-02-20 14:57:17","number":3,"deleted_at":null}},{"id":88,"user_id":21,"conference_id":35,"created_at":"2019-03-17 10:50:09","updated_at":"2019-03-17 10:50:09","status":0,"remark":null,"company_id":1,"user":{"id":21,"name":"楚昌洪","account":"楚昌洪","phone":null,"mail":null,"status":1,"headimg":"http://oa.zycnb.net/admin/img/head_img.jpeg","password":"$2y$10$U4Y9/eofLZUYEn/fJMhFueUgX0Gl9twiqziFFPMqnMmB1R3GuMYq6","remark":null,"is_admin":0,"company_id":1,"department_id":4,"position_id":5,"company_name":null,"company_logo":null,"remember_token":"","created_at":"2019-02-20 14:58:01","updated_at":"2019-02-20 14:58:01","number":4,"deleted_at":null}},{"id":89,"user_id":22,"conference_id":35,"created_at":"2019-03-17 10:50:09","updated_at":"2019-03-17 10:50:09","status":0,"remark":null,"company_id":1,"user":{"id":22,"name":"杜识宝","account":"杜识宝","phone":null,"mail":null,"status":1,"headimg":"http://oa.zycnb.net/admin/img/head_img.jpeg","password":"$2y$10$as7yp17FQstq1vocyR3zVe9pasFn5ZWo0yLGAA5mvc1/WKM3gJlpq","remark":null,"is_admin":0,"company_id":1,"department_id":4,"position_id":5,"company_name":null,"company_logo":null,"remember_token":"","created_at":"2019-02-20 14:58:19","updated_at":"2019-02-20 14:58:19","number":5,"deleted_at":null}},{"id":90,"user_id":23,"conference_id":35,"created_at":"2019-03-17 10:50:09","updated_at":"2019-03-17 10:50:09","status":0,"remark":null,"company_id":1,"user":{"id":23,"name":"胡伟","account":"胡伟","phone":null,"mail":null,"status":1,"headimg":"http://oa.zycnb.net/admin/img/head_img.jpeg","password":"$2y$10$wOlSZZq9UFPosH.Ouqrt9OtsVxE9MKOmqmp1lwEJMIybCMV4XoAaC","remark":null,"is_admin":0,"company_id":1,"department_id":4,"position_id":5,"company_name":null,"company_logo":null,"remember_token":"","created_at":"2019-02-20 14:58:42","updated_at":"2019-02-20 14:58:42","number":6,"deleted_at":null}},{"id":91,"user_id":24,"conference_id":35,"created_at":"2019-03-17 10:50:09","updated_at":"2019-03-17 10:50:09","status":0,"remark":null,"company_id":1,"user":{"id":24,"name":"王华勇","account":"王华勇","phone":null,"mail":null,"status":1,"headimg":"http://oa.zycnb.net/admin/img/head_img.jpeg","password":"$2y$10$fsORPAq57pN6EuIMr74S/.yUHmQQihcm.IG3U8ODws0qzViIX1Ydu","remark":null,"is_admin":0,"company_id":1,"department_id":4,"position_id":5,"company_name":null,"company_logo":null,"remember_token":"","created_at":"2019-02-20 14:59:01","updated_at":"2019-02-20 14:59:01","number":7,"deleted_at":null}},{"id":92,"user_id":25,"conference_id":35,"created_at":"2019-03-17 10:50:09","updated_at":"2019-03-17 10:50:09","status":0,"remark":null,"company_id":1,"user":{"id":25,"name":"谢隽峰","account":"谢隽峰","phone":null,"mail":null,"status":1,"headimg":"http://oa.zycnb.net/admin/img/head_img.jpeg","password":"$2y$10$X1CLaKFBQp0kJpb1dvcQt.qxNwXCasVk1lhRdnnaKamyWp.zjTL9C","remark":null,"is_admin":0,"company_id":1,"department_id":4,"position_id":5,"company_name":null,"company_logo":null,"remember_token":"","created_at":"2019-02-20 14:59:21","updated_at":"2019-02-20 14:59:21","number":0,"deleted_at":null}},{"id":93,"user_id":26,"conference_id":35,"created_at":"2019-03-17 10:50:09","updated_at":"2019-03-17 10:50:09","status":0,"remark":null,"company_id":1,"user":{"id":26,"name":"莫林川","account":"莫林川","phone":null,"mail":null,"status":1,"headimg":"http://oa.zycnb.net/admin/img/head_img.jpeg","password":"$2y$10$h1oynYO2oM2z3k3thKZo7Ou5MghuobXj5.lKMHZJFN2pAKdI4Gzz.","remark":null,"is_admin":0,"company_id":1,"department_id":4,"position_id":6,"company_name":null,"company_logo":null,"remember_token":"","created_at":"2019-02-20 14:59:42","updated_at":"2019-02-20 14:59:42","number":1,"deleted_at":null}},{"id":94,"user_id":27,"conference_id":35,"created_at":"2019-03-17 10:50:09","updated_at":"2019-03-17 10:50:09","status":0,"remark":null,"company_id":1,"user":{"id":27,"name":"陈建","account":"陈建","phone":null,"mail":null,"status":1,"headimg":"http://oa.zycnb.net/admin/img/head_img.jpeg","password":"$2y$10$QIaJ3QOWS0JDg0o763aU2O9KBmFJMMSDByTQfQxIcWJiGvdntRFle","remark":null,"is_admin":0,"company_id":1,"department_id":4,"position_id":6,"company_name":null,"company_logo":null,"remember_token":"","created_at":"2019-02-20 15:00:02","updated_at":"2019-02-20 15:00:02","number":2,"deleted_at":null}},{"id":95,"user_id":28,"conference_id":35,"created_at":"2019-03-17 10:50:09","updated_at":"2019-03-17 10:50:09","status":0,"remark":null,"company_id":1,"user":{"id":28,"name":"高超","account":"高超","phone":null,"mail":null,"status":1,"headimg":"http://oa.zycnb.net/admin/img/head_img.jpeg","password":"$2y$10$CIzUNuj8nz0HgqseC6SNtOILsiwt6boadu4AFN0qURZG65BPp.25C","remark":null,"is_admin":0,"company_id":1,"department_id":4,"position_id":6,"company_name":null,"company_logo":null,"remember_token":"","created_at":"2019-02-20 15:00:27","updated_at":"2019-02-20 15:00:39","number":3,"deleted_at":null}},{"id":96,"user_id":29,"conference_id":35,"created_at":"2019-03-17 10:50:09","updated_at":"2019-03-17 10:50:09","status":0,"remark":null,"company_id":1,"user":{"id":29,"name":"张杰","account":"张杰","phone":null,"mail":null,"status":1,"headimg":"http://oa.zycnb.net/admin/img/head_img.jpeg","password":"$2y$10$NmNQtm9h/68vQfgFsErJIetZ/eq2qAPyH7rsSWjZaruTT3jLf7OQu","remark":null,"is_admin":0,"company_id":1,"department_id":4,"position_id":6,"company_name":null,"company_logo":null,"remember_token":"WobicX0hoJnJKue2H6k2ywmvHIcnW9uJleGTMjKHsBGH8PbrrayXfYLPe3FY","created_at":"2019-02-20 15:00:57","updated_at":"2019-02-21 20:49:14","number":4,"deleted_at":2019}},{"id":97,"user_id":30,"conference_id":35,"created_at":"2019-03-17 10:50:09","updated_at":"2019-03-17 10:50:09","status":0,"remark":null,"company_id":1,"user":{"id":30,"name":"胡波","account":"胡波","phone":null,"mail":null,"status":1,"headimg":"http://oa.zycnb.net/admin/img/head_img.jpeg","password":"$2y$10$tEsvlzd3fyUpCI3JsY5Ez.q/XsWsL8m1Xf66hctJAOhTPvVyQRU3W","remark":null,"is_admin":0,"company_id":1,"department_id":4,"position_id":6,"company_name":null,"company_logo":null,"remember_token":"","created_at":"2019-02-20 15:01:16","updated_at":"2019-02-20 15:01:16","number":5,"deleted_at":null}},{"id":98,"user_id":31,"conference_id":35,"created_at":"2019-03-17 10:50:09","updated_at":"2019-03-17 10:50:09","status":0,"remark":null,"company_id":1,"user":{"id":31,"name":"李坤鹏","account":"李坤鹏","phone":null,"mail":null,"status":1,"headimg":"http://oa.zycnb.net/admin/img/head_img.jpeg","password":"$2y$10$ib8er91A4herEKqdqIRQ8uvRhtRKK8AmVIBUGrk3EdF.uu.cjda/m","remark":null,"is_admin":0,"company_id":1,"department_id":4,"position_id":6,"company_name":null,"company_logo":null,"remember_token":"","created_at":"2019-02-20 15:01:41","updated_at":"2019-02-20 15:01:41","number":6,"deleted_at":null}},{"id":99,"user_id":32,"conference_id":35,"created_at":"2019-03-17 10:50:09","updated_at":"2019-03-17 10:50:09","status":0,"remark":null,"company_id":1,"user":{"id":32,"name":"罗喆","account":"罗喆","phone":null,"mail":null,"status":1,"headimg":"http://oa.zycnb.net/admin/img/head_img.jpeg","password":"$2y$10$vA2/2YaSt6nLF.YrPS3T/uVqn9VFr4VNedrQ7wW1emloZ59gXcm5i","remark":null,"is_admin":0,"company_id":1,"department_id":4,"position_id":6,"company_name":null,"company_logo":null,"remember_token":"","created_at":"2019-02-20 15:02:03","updated_at":"2019-02-20 15:02:03","number":7,"deleted_at":null}},{"id":100,"user_id":33,"conference_id":35,"created_at":"2019-03-17 10:50:09","updated_at":"2019-03-17 10:50:09","status":0,"remark":null,"company_id":1,"user":{"id":33,"name":"蒋东莉","account":"蒋东莉","phone":null,"mail":null,"status":1,"headimg":"http://oa.zycnb.net/admin/img/head_img.jpeg","password":"$2y$10$0mTEJ38mCosIPpXHbv.STOOHAru8duM1CkEmE9f2T8gUbWpLszf5m","remark":null,"is_admin":0,"company_id":1,"department_id":4,"position_id":6,"company_name":null,"company_logo":null,"remember_token":"","created_at":"2019-02-20 15:02:21","updated_at":"2019-02-20 15:02:21","number":8,"deleted_at":null}},{"id":101,"user_id":34,"conference_id":35,"created_at":"2019-03-17 10:50:09","updated_at":"2019-03-17 10:50:09","status":0,"remark":null,"company_id":1,"user":{"id":34,"name":"综合室","account":"x1","phone":null,"mail":null,"status":1,"headimg":"http://oa.zycnb.net/admin/img/head_img.jpeg","password":"$2y$10$6hLmAnF0l1wjZbaBm5pu.eFR2myVAQKJkppMmfNTQaMIRqUp0CGa.","remark":null,"is_admin":0,"company_id":1,"department_id":14,"position_id":7,"company_name":null,"company_logo":null,"remember_token":"","created_at":"2019-02-21 20:35:02","updated_at":"2019-02-21 20:35:02","number":1,"deleted_at":null}},{"id":102,"user_id":35,"conference_id":35,"created_at":"2019-03-17 10:50:09","updated_at":"2019-03-17 10:50:09","status":0,"remark":null,"company_id":1,"user":{"id":35,"name":"副主任1","account":"x2","phone":null,"mail":null,"status":1,"headimg":"http://oa.zycnb.net/admin/img/head_img.jpeg","password":"$2y$10$rAS9hOz78HsjiowrZ4LGke4rUbWU0CwfgXR7reqonlDh7pS8a6sNm","remark":null,"is_admin":0,"company_id":1,"department_id":15,"position_id":8,"company_name":null,"company_logo":null,"remember_token":"hi9Bn1D3SStringNq6jpFKlML2Bp1ELazcLkY7m1d1loDPycoaJ5QQ1CCsAIJ25","created_at":"2019-02-21 20:38:47","updated_at":"2019-02-21 20:40:59","number":2,"deleted_at":null}},{"id":103,"user_id":36,"conference_id":35,"created_at":"2019-03-17 10:50:09","updated_at":"2019-03-17 10:50:09","status":0,"remark":null,"company_id":1,"user":{"id":36,"name":"副主任2","account":"x3","phone":null,"mail":null,"status":1,"headimg":"http://oa.zycnb.net/admin/img/head_img.jpeg","password":"$2y$10$/.0HbMwMy2ZkeeHXi6GP8OaFDV7uiR1ncudIuarhwoqS3gvbZa.V.","remark":null,"is_admin":0,"company_id":1,"department_id":21,"position_id":9,"company_name":null,"company_logo":null,"remember_token":"","created_at":"2019-02-21 20:40:10","updated_at":"2019-02-21 20:41:12","number":2,"deleted_at":null}},{"id":104,"user_id":37,"conference_id":35,"created_at":"2019-03-17 10:50:09","updated_at":"2019-03-17 10:50:09","status":0,"remark":null,"company_id":1,"user":{"id":37,"name":"副主任3","account":"x4","phone":null,"mail":null,"status":1,"headimg":"http://oa.zycnb.net/admin/img/head_img.jpeg","password":"$2y$10$13M15ed/Hu7bw4jmqIdrCuUAAyihKAsLT81kJcmJ5kQob552AbC9G","remark":null,"is_admin":0,"company_id":1,"department_id":24,"position_id":10,"company_name":null,"company_logo":null,"remember_token":"","created_at":"2019-02-21 20:42:47","updated_at":"2019-02-21 20:42:47","number":3,"deleted_at":null}},{"id":105,"user_id":38,"conference_id":35,"created_at":"2019-03-17 10:50:09","updated_at":"2019-03-17 10:50:09","status":0,"remark":null,"company_id":1,"user":{"id":38,"name":"副主任4","account":"x5","phone":null,"mail":null,"status":1,"headimg":"http://oa.zycnb.net/admin/img/head_img.jpeg","password":"$2y$10$5JYa4EjEBg6fIf2rMZVCkOnZlcsHTOvbWZ/GyViN5p/TKsInJRKbu","remark":null,"is_admin":0,"company_id":1,"department_id":22,"position_id":11,"company_name":null,"company_logo":null,"remember_token":"","created_at":"2019-02-21 20:43:58","updated_at":"2019-02-21 20:44:22","number":4,"deleted_at":null}},{"id":106,"user_id":39,"conference_id":35,"created_at":"2019-03-17 10:50:09","updated_at":"2019-03-17 10:50:09","status":0,"remark":null,"company_id":1,"user":{"id":39,"name":"cs3","account":"cs3","phone":null,"mail":null,"status":1,"headimg":"http://www.fly.com/admin/img/head_img.jpeg","password":"$2y$10$V4nRSEylIbLE3mJEcIWooeUyHd/yM5BSqE5kdf69wJ5Wa1ij.32Fm","remark":null,"is_admin":0,"company_id":1,"department_id":1,"position_id":3,"company_name":null,"company_logo":null,"remember_token":"bKWdvzrKXvLjT2hKoV2yZxjSK7rTNLmmu5McuqAVjUWpvXmQscmUN7OaNNLg","created_at":"2019-03-02 17:02:25","updated_at":"2019-03-02 17:02:41","number":null,"deleted_at":null}}]
     * conference_room_use : [{"id":44,"conference_id":35,"company_id":1,"created_at":"2019-03-17 10:50:09","updated_at":"2019-03-17 10:50:09","start_time":"2019-03-17 11:00:00","end_time":"2019-03-17 12:00:00","conference_room_id":1,"hour":11,"date":"2019-03-17","room":{"id":1,"name":"1-1","company_id":1,"created_at":"2019-03-02 17:30:24","updated_at":"2019-03-02 17:30:24"}},{"id":45,"conference_id":35,"company_id":1,"created_at":"2019-03-17 10:50:09","updated_at":"2019-03-17 10:50:09","start_time":"2019-03-17 12:00:00","end_time":"2019-03-17 13:00:00","conference_room_id":1,"hour":12,"date":"2019-03-17","room":{"id":1,"name":"1-1","company_id":1,"created_at":"2019-03-02 17:30:24","updated_at":"2019-03-02 17:30:24"}},{"id":46,"conference_id":35,"company_id":1,"created_at":"2019-03-17 10:50:09","updated_at":"2019-03-17 10:50:09","start_time":"2019-03-17 13:00:00","end_time":"2019-03-17 14:00:00","conference_room_id":1,"hour":13,"date":"2019-03-17","room":{"id":1,"name":"1-1","company_id":1,"created_at":"2019-03-02 17:30:24","updated_at":"2019-03-02 17:30:24"}},{"id":47,"conference_id":35,"company_id":1,"created_at":"2019-03-17 10:50:09","updated_at":"2019-03-17 10:50:09","start_time":"2019-03-17 14:00:00","end_time":"2019-03-17 15:00:00","conference_room_id":1,"hour":14,"date":"2019-03-17","room":{"id":1,"name":"1-1","company_id":1,"created_at":"2019-03-02 17:30:24","updated_at":"2019-03-02 17:30:24"}}]
     * conference_user_one : {"id":106,"user_id":39,"conference_id":35,"created_at":"2019-03-17 10:50:09","updated_at":"2019-03-17 10:50:09","status":0,"remark":null,"company_id":1}
     * conference_type : {"id":1,"name":"会议类型1","company_id":1,"created_at":"2019-03-02 17:47:37","updated_at":"2019-03-02 17:47:37"}
     */

    private String id;
    private String name;
    private String company_id;
    private String conference_type_id;
    private String created_at;
    private String updated_at;
    private String status;
    private String compere_name;
    private String age_name;
    private String linkman;
    private String contact;
    private String type;
    private String start_time;
    private String end_time;
    private String user_ids;
    private String user_ids_name;
    private String conference_room;
    private String conference_room_names;
    private String date;
    private String now_status;
    private String now_status_name;
    private String conference_type_name;
    private ConferenceUserOneBean conference_user_one;
    private ConferenceTypeBean conference_type;
    private List<VStrBean> vStr;
    private List<ConferenceUserBean> conference_user;
    private List<ConferenceRoomUseBean> conference_room_use;

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

    public String getConference_type_id() {
        return conference_type_id;
    }

    public void setConference_type_id(String conference_type_id) {
        this.conference_type_id = conference_type_id;
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

    public String getCompere_name() {
        return compere_name;
    }

    public void setCompere_name(String compere_name) {
        this.compere_name = compere_name;
    }

    public String getAge_name() {
        return age_name;
    }

    public void setAge_name(String age_name) {
        this.age_name = age_name;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getConference_room() {
        return conference_room;
    }

    public void setConference_room(String conference_room) {
        this.conference_room = conference_room;
    }

    public String getConference_room_names() {
        return conference_room_names;
    }

    public void setConference_room_names(String conference_room_names) {
        this.conference_room_names = conference_room_names;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNow_status() {
        return now_status;
    }

    public void setNow_status(String now_status) {
        this.now_status = now_status;
    }

    public String getNow_status_name() {
        return now_status_name;
    }

    public void setNow_status_name(String now_status_name) {
        this.now_status_name = now_status_name;
    }

    public String getConference_type_name() {
        return conference_type_name;
    }

    public void setConference_type_name(String conference_type_name) {
        this.conference_type_name = conference_type_name;
    }

    public ConferenceUserOneBean getConference_user_one() {
        return conference_user_one;
    }

    public void setConference_user_one(ConferenceUserOneBean conference_user_one) {
        this.conference_user_one = conference_user_one;
    }

    public ConferenceTypeBean getConference_type() {
        return conference_type;
    }

    public void setConference_type(ConferenceTypeBean conference_type) {
        this.conference_type = conference_type;
    }

    public List<VStrBean> getVStr() {
        return vStr;
    }

    public void setVStr(List<VStrBean> vStr) {
        this.vStr = vStr;
    }

    public List<ConferenceUserBean> getConference_user() {
        return conference_user;
    }

    public void setConference_user(List<ConferenceUserBean> conference_user) {
        this.conference_user = conference_user;
    }

    public List<ConferenceRoomUseBean> getConference_room_use() {
        return conference_room_use;
    }

    public void setConference_room_use(List<ConferenceRoomUseBean> conference_room_use) {
        this.conference_room_use = conference_room_use;
    }

    public static class ConferenceUserOneBean {
        /**
         * id : 106
         * user_id : 39
         * conference_id : 35
         * created_at : 2019-03-17 10:50:09
         * updated_at : 2019-03-17 10:50:09
         * status : 0
         * remark : null
         * company_id : 1
         */

        private String id;
        private String user_id;
        private String conference_id;
        private String created_at;
        private String updated_at;
        private String status;
        private String remark;
        private String company_id;

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

        public String getConference_id() {
            return conference_id;
        }

        public void setConference_id(String conference_id) {
            this.conference_id = conference_id;
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

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getCompany_id() {
            return company_id;
        }

        public void setCompany_id(String company_id) {
            this.company_id = company_id;
        }
    }

    public static class ConferenceTypeBean {
        /**
         * id : 1
         * name : 会议类型1
         * company_id : 1
         * created_at : 2019-03-02 17:47:37
         * updated_at : 2019-03-02 17:47:37
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

    public static class VStrBean {
        /**
         * conference_room_id : 1
         * date : 2019-03-17
         * hour : 11
         * key : 2c72f701c2a6f8483c7e4f27532c1160
         */

        private String conference_room_id;
        private String date;
        private String hour;
        private String key;

        public String getConference_room_id() {
            return conference_room_id;
        }

        public void setConference_room_id(String conference_room_id) {
            this.conference_room_id = conference_room_id;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getHour() {
            return hour;
        }

        public void setHour(String hour) {
            this.hour = hour;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }

    public static class ConferenceUserBean {
        /**
         * id : 76
         * user_id : 11
         * conference_id : 35
         * created_at : 2019-03-17 10:50:09
         * updated_at : 2019-03-17 10:50:09
         * status : 0
         * remark : null
         * company_id : 1
         * user : {"id":11,"name":"王英","account":"王英","phone":null,"mail":"admin","status":1,"headimg":"http://oa.zycnb.net/admin/img/head_img.jpeg","password":"$2y$10$8vQEU0tm9jIugGW7Y8AwseD9zZJon/btql3cfR1oZ3/xnbibFss2q","remark":null,"is_admin":0,"company_id":1,"department_id":11,"position_id":3,"company_name":null,"company_logo":null,"remember_token":"","created_at":"2019-02-20 14:50:47","updated_at":"2019-02-20 14:52:37","number":2,"deleted_at":null}
         */

        private String id;
        private String user_id;
        private String conference_id;
        private String created_at;
        private String updated_at;
        private String status;
        private String remark;
        private String company_id;
        private UserBean user;

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

        public String getConference_id() {
            return conference_id;
        }

        public void setConference_id(String conference_id) {
            this.conference_id = conference_id;
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

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getCompany_id() {
            return company_id;
        }

        public void setCompany_id(String company_id) {
            this.company_id = company_id;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean {
            /**
             * id : 11
             * name : 王英
             * account : 王英
             * phone : null
             * mail : admin
             * status : 1
             * headimg : http://oa.zycnb.net/admin/img/head_img.jpeg
             * password : $2y$10$8vQEU0tm9jIugGW7Y8AwseD9zZJon/btql3cfR1oZ3/xnbibFss2q
             * remark : null
             * is_admin : 0
             * company_id : 1
             * department_id : 11
             * position_id : 3
             * company_name : null
             * company_logo : null
             * remember_token : 
             * created_at : 2019-02-20 14:50:47
             * updated_at : 2019-02-20 14:52:37
             * number : 2
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

    public static class ConferenceRoomUseBean {
        /**
         * id : 44
         * conference_id : 35
         * company_id : 1
         * created_at : 2019-03-17 10:50:09
         * updated_at : 2019-03-17 10:50:09
         * start_time : 2019-03-17 11:00:00
         * end_time : 2019-03-17 12:00:00
         * conference_room_id : 1
         * hour : 11
         * date : 2019-03-17
         * room : {"id":1,"name":"1-1","company_id":1,"created_at":"2019-03-02 17:30:24","updated_at":"2019-03-02 17:30:24"}
         */

        private String id;
        private String conference_id;
        private String company_id;
        private String created_at;
        private String updated_at;
        private String start_time;
        private String end_time;
        private String conference_room_id;
        private String hour;
        private String date;
        private RoomBean room;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getConference_id() {
            return conference_id;
        }

        public void setConference_id(String conference_id) {
            this.conference_id = conference_id;
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

        public String getConference_room_id() {
            return conference_room_id;
        }

        public void setConference_room_id(String conference_room_id) {
            this.conference_room_id = conference_room_id;
        }

        public String getHour() {
            return hour;
        }

        public void setHour(String hour) {
            this.hour = hour;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public RoomBean getRoom() {
            return room;
        }

        public void setRoom(RoomBean room) {
            this.room = room;
        }

        public static class RoomBean {
            /**
             * id : 1
             * name : 1-1
             * company_id : 1
             * created_at : 2019-03-02 17:30:24
             * updated_at : 2019-03-02 17:30:24
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
