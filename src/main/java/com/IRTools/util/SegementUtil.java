package com.IRTools.util;

import com.IRTools.preprocess.TextPreprocess;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.CustomDictionary;

public class SegementUtil {
    public static String segement(String str){
        TextPreprocess textPreprocess = new TextPreprocess(str);
        return textPreprocess.doChineseFileProcess();
    }


    public static void main(String[] args) {
        String str = "[【超级账号】角色卡片折叠、角色搜素, 【超级账号】成长线, 【超级账号】PVUV权限控制, 【超级账号】盟外角色、联系人梳理, 超级账号41周收集反馈, 【超级账号】音视频通话2.0, 【矿场】矿场系统&超级账号整合初步计划, 【超级账号】实名认证升级, 【易用性】超级账号主系统易用性问题记录, 【超级账号】个人企业空间信息升级, 超级账号农场项目-目标&进度规划, 总分公司的超级账号使用方式, 【超级账号】文件上传组件梳理统一, 【超级账号】库的分组、折叠, 超级账号_1.1_审批升级后续迭代-三端, 【超级账号】思目平衡发展基金持币表、持币榜、准备金率, 【超级账号】目标升级, 【超级账号】“我的”迭代, 【会议纪要】7.19超级账号未来的几点可能变更, 【超级账号】文件回收站、清空、会员机制, 「使用培训」10.23物资部及供应商超级账号使用培训, AppleID登录注册, 「使用培训」12.21餐厅超级账号使用培训, 会议纪要-20190129会议-回头看超级账号, Jan/31/2020 晚间会议纪要, 超级账号39周收集反馈, 【超级账号】目标人员复用, 超级账号配合年会各部门协作方案, 【超级账号】空间功能放开, 【超级账号】用户反馈记录, 「使用培训」10.30企划部超级账号使用培训, 【超级账号】注册登录简化、强制绑定手机号、成员邀请去掉邮箱、LDAP多个绑定, 【超级账号】系统数据分析后台（PVUV）, 【超级账号】个人盟迭代1.2, 【超级账号】个人盟迭代1.3, 【超级账号】邀请注册机制]";
        CustomDictionary.add("衣食住行");
        CustomDictionary.add("需求迭代清单");
        CustomDictionary.add("超级账号");
        System.out.println(HanLP.extractKeyword(str, 2));
        System.out.println(HanLP.extractSummary(str, 2));
        System.out.println(HanLP.segment(""));
        System.out.println(segement(str));
    }
}
