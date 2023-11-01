package com.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.springframework.util.StringUtils;

/**
 * ZIP压缩文件操作工具类
 * 支持密码
 * 依赖zip4j开源项目(http://www.lingala.net/zip4j/)
 * 版本1.3.1
 * @author ninemax
 */
public class ZipUtil {

    /**
     * 使用给定密码解压指定的ZIP压缩文件到指定目录
     * <p>
     * 如果指定目录不存在,可以自动创建,不合法的路径将导致异常被抛出
     * @param zip 指定的ZIP压缩文件
     * @param dest 解压目录
     * @param passwd ZIP文件的密码
     * @return 解压后文件数组
     * @throws ZipException 压缩文件有损坏或者解压缩失败抛出
     */
    public static File [] unzip(String zip, String dest, String passwd) throws ZipException {
        File zipFile = new File(zip);
        return unzip(zipFile, dest, passwd);
    }

    /**
     * 使用给定密码解压指定的ZIP压缩文件到指定目录
     * <p>
     * 如果指定目录不存在,可以自动创建,不合法的路径将导致异常被抛出
     * @param zipFile 指定的ZIP压缩文件
     * @param dest 解压目录
     * @param passwd ZIP文件的密码
     * @return  解压后文件数组
     * @throws ZipException 压缩文件有损坏或者解压缩失败抛出
     */
    public static File [] unzip(File zipFile, String dest, String passwd) throws ZipException {
        ZipFile zFile = new ZipFile(zipFile);
        zFile.setFileNameCharset("GBK");
        if (!zFile.isValidZipFile()) {
            throw new ZipException("压缩文件不合法,可能被损坏.");
        }
        File destDir = new File(dest);
        if (destDir.isDirectory() && !destDir.exists()) {
            destDir.mkdir();
        }
        if (zFile.isEncrypted()) {
            zFile.setPassword(passwd.toCharArray());
        }
        zFile.extractAll(dest);

        List<FileHeader> headerList = zFile.getFileHeaders();
        List<File> extractedFileList = new ArrayList<File>();
        for(FileHeader fileHeader : headerList) {
            if (!fileHeader.isDirectory()) {
                extractedFileList.add(new File(destDir,fileHeader.getFileName()));
            }
        }
        File [] extractedFiles = new File[extractedFileList.size()];
        extractedFileList.toArray(extractedFiles);
        return extractedFiles;
    }



    /**
     * 使用给定密码压缩指定文件或文件夹到当前目录
     * @param src 要压缩的文件
     * @param dest 压缩文件存放路径
     * @param passwd 压缩使用的密码
     * @return 最终的压缩文件存放的绝对路径,如果为null则说明压缩失败.
     */
    public static String zip(String src, String dest, String passwd) {
        return zip(src, dest, false, passwd);
    }

    /**
     * 使用给定密码压缩指定文件或文件夹到指定位置.
     * <p>
     * dest可传最终压缩文件存放的绝对路径,也可以传存放目录,也可以传null或者"".<br />
     * 如果传null或者""则将压缩文件存放在当前目录,即跟源文件同目录,压缩文件名取源文件名,以.zip为后缀;<br />
     * 如果以路径分隔符(File.separator)结尾,则视为目录,压缩文件名取源文件名,以.zip为后缀,否则视为文件名.
     * @param src 要压缩的文件或文件夹路径
     * @param dest 压缩文件存放路径
     * @param isCreateDir 是否在压缩文件里创建目录,仅在压缩文件为目录时有效.<br />
     * 如果为false,将直接压缩目录下文件到压缩文件.
     * @param passwd 压缩使用的密码
     * @return 最终的压缩文件存放的绝对路径,如果为null则说明压缩失败.
     */
    public static String zip(String src, String dest, boolean isCreateDir, String passwd) {
        File srcFile = new File(src);
        dest = buildDestinationZipFilePath(srcFile, dest);
        ZipParameters parameters = new ZipParameters();
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);           // 压缩方式
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);    // 压缩级别
        if (!StringUtils.isEmpty(passwd)) {
            parameters.setEncryptFiles(true);
            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD); // 加密方式
            parameters.setPassword(passwd.toCharArray());
        }
        try {
            ZipFile zipFile = new ZipFile(dest);
            if (srcFile.isDirectory()) {
                // 如果不创建目录的话,将直接把给定目录下的文件压缩到压缩文件,即没有目录结构
                if (!isCreateDir) {
                    File [] subFiles = srcFile.listFiles();
                    ArrayList<File> temp = new ArrayList<File>();
                    Collections.addAll(temp, subFiles);
                    zipFile.addFiles(temp, parameters);
                    return dest;
                }
                zipFile.addFolder(srcFile, parameters);
            } else {
                zipFile.addFile(srcFile, parameters);
            }
            return dest;
        } catch (ZipException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 构建压缩文件存放路径,如果不存在将会创建
     * 传入的可能是文件名或者目录,也可能不传,此方法用以转换最终压缩文件的存放路径
     * @param srcFile 源文件
     * @param destParam 压缩目标路径
     * @return 正确的压缩文件存放路径
     */
    private static String buildDestinationZipFilePath(File srcFile,String destParam) {
        if (StringUtils.isEmpty(destParam)) {
            if (srcFile.isDirectory()) {
                destParam = srcFile.getParent() + File.separator + srcFile.getName() + ".zip";
            } else {
                String fileName = srcFile.getName().substring(0, srcFile.getName().lastIndexOf("."));
                destParam = srcFile.getParent() + File.separator + fileName + ".zip";
            }
        } else {
            createDestDirectoryIfNecessary(destParam);  // 在指定路径不存在的情况下将其创建出来
            if (destParam.endsWith(File.separator)) {
                String fileName = "";
                if (srcFile.isDirectory()) {
                    fileName = srcFile.getName();
                } else {
                    fileName = srcFile.getName().substring(0, srcFile.getName().lastIndexOf("."));
                }
                destParam += fileName + ".zip";
            }
        }
        return destParam;
    }

    /**
     * 在必要的情况下创建压缩文件存放目录,比如指定的存放路径并没有被创建
     * @param destParam 指定的存放路径,有可能该路径并没有被创建
     */
    private static void createDestDirectoryIfNecessary(String destParam) {
        File destDir = null;
        if (destParam.endsWith(File.separator)) {
            destDir = new File(destParam);
        } else {
            destDir = new File(destParam.substring(0, destParam.lastIndexOf(File.separator)));
        }
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
    }

//    public static void main(String[] args) {
//        String msg = "";
//        try {
//            unzip("D:\\home\\cc.zip","D:\\home\\pic1",  "11");
//        } catch (ZipException e) {
//            e.printStackTrace();
//            msg = e.getMessage();
//        }
//        if(msg.contains("Wrong Password")){
//            System.out.println("解压缩密码错误");
//        }
////      try {
////          File[] files = unzip("d:\\test\\汉字.zip", "aa");
////          for (int i = 0; i < files.length; i++) {
////              System.out.println(files[i]);
////          }
////      } catch (ZipException e) {
////          e.printStackTrace();
////      }
//    }
public static void main(String[] args) {
    String s = "<script id=\"__NEXT_DATA__\" type=\"application/json\">\n" +
            "\t\t\t\t{\"props\":{\"pageProps\":{\"dehydratedState\":{\"mutations\":[],\"queries\":[{\"state\":{\"data\":{\"state\":\"ok\",\"message\":\"\",\"special\":\"\",\"vipMessage\":\"\",\"isLogin\":0,\"errorCode\":0,\"data\":{\"historyNames\":\"保定清山房地产开发有限公司\",\"regStatus\":\"存续\",\"estiblishTimeTitleName\":\"成立日期\",\"websiteRiskType\":3,\"emailList\":[\"402064796@qq.com\",\"1053278449@qq.com\"],\"listedPlateList\":[],\"phoneList\":[\"0312-325****\",\"0312-327****\"],\"type\":1,\"equityUrl\":\"https://cdn.tianyancha.com/equity/7ee3ab214e6621063f1cf62d75ec101b.png\",\"legalPersonType\":1,\"sensitiveEntityType\":4,\"property3\":\"\",\"companyShowBizTypeName\":\"其他有限责任公司\",\"regCapitalLabel\":\"超大型\",\"companyProfilePlainText\":\"清山房地产开发有限公司（曾用名：保定清山房地产开发有限公司），成立于2002年，位于河北省保定市，是一家以从事房地产业为主的企业。企业注册资本80000万人民币，实缴资本72300万人民币。通过天眼查大数据分析，清山房地产开发有限公司共对外投资了15家企业，参与招投标项目11次；知识产权方面有商标信息60条，著作权信息1条；此外企业还拥有行政许可12个。风险方面共发现企业有法律诉讼63条，涉案总金额3817.103627万元；开庭公告21条，立案信息1条。\",\"approvedTime\":1539532800000,\"industry2017\":\"房地产业\",\"logo\":\"https://img5.tianyancha.com/logo/lll/115612fda69f56c2cd3cb4522b23a047.png@!f_200x200\",\"id\":29459282,\"originalPercentileScore\":9708,\"orgNumber\":\"73873719-9\",\"isClaimed\":0,\"listedStatusTypeForSenior\":0,\"companyBaseInfoSubtitleList\":{},\"taxPhone\":\"0312-3250999\",\"taxBankName\":\"建行保定城建支行\",\"entityType\":1,\"companyBizOrgType\":\"1190\",\"businessScope\":\"房地产开发与经营，建筑材料、建筑机械设备租赁、销售，工程管理服务，园林绿化工程施工，土地整理。（法律、行政法规或者国务院决定规定须报经批准的项目，未获批准前不准经营）\",\"taxNumber\":\"91130600738737199K\",\"regCapitalCurrency\":\"人民币\",\"tags\":null,\"regCapitalAmount\":\"80000\",\"phoneNumber\":\"0312-325****\",\"taxQualification\":\"增值税一般纳税人\",\"name\":\"清山房地产开发有限公司\",\"percentileScore\":9708,\"extraInfo\":null,\"baseInfo\":\"清山房地产开发有限公司（曾用名：保定清山房地产开发有限公司），成立于2002年，位于河北省保定市，是一家以从事房地产业为主的企业。企业注册资本80000万人民币，实缴资本72300万人民币。通过天眼查大数据分析，清山房地产开发有限公司共对外投资了15家企业，参与招投标项目11次；知识产权方面有商标信息60条，著作权信息1条；此外企业还拥有行政许可12个。风险方面共发现企业有法律诉讼63条，涉案总金额3817.103627万元；开庭公告21条，立案信息1条。\",\"regCapital\":\"80000万人民币\",\"regLocationTitle\":\"注册地址\",\"staffNumRange\":\"50-99人\",\"link\":1,\"industry\":\"房地产业\",\"legalTitleName\":\"法定代表人\",\"regTitleName\":\"注册资本\",\"updateTimes\":1683541267000,\"legalPersonName\":\"于强\",\"tagListV2\":[{\"tagId\":30,\"title\":\"存续\",\"name\":\"存续\",\"logo\":\"\",\"color\":\"#119944\",\"background\":\"#ECF7F0\",\"hover\":\"\",\"clickHyperLinkType\":0,\"clickUrl\":\"\"},{\"tagId\":1,\"title\":\"曾用名\",\"name\":\"曾用名\",\"logo\":\"\",\"color\":\"#757DD3\",\"background\":\"#F0F1FA\",\"hover\":\"保定清山房地产开发有限公司\\\\n(2002-05 至 2011-09)\",\"clickHyperLinkType\":0,\"clickUrl\":\"\"},{\"tagId\":24,\"title\":\"小微企业\",\"name\":\"小微企业\",\"logo\":\"\",\"color\":\"#0084FF\",\"background\":\"#EBF5FF\",\"hover\":\"小微企业概念来自工信部《中小企业划型标准规定》，具体标准根据企业从业人员、营业收入、资产总额等指标，结合行业特点制定。\",\"clickHyperLinkType\":0,\"clickUrl\":\"\"}],\"regNumber\":\"130600000036195\",\"creditCode\":\"91130600738737199K\",\"fromTime\":1020787200000,\"socialStaffNum\":58,\"companyOrgType\":\"其他有限责任公司\",\"alias\":\"清山\",\"taxAddress\":\"保定市竞秀区天威西路263号\",\"email\":\"402064796@qq.com\",\"actualCapital\":\"72300万人民币\",\"phoneSourceList\":[{\"phoneNumber\":\"0312-325****\",\"oriPhoneNumber\":null,\"showSource\":\"2021年年报\",\"hasMoreCompany\":1,\"companyCount\":0,\"companyCountStr\":\"0\",\"companyTotalStr\":\"7\",\"phoneType\":9,\"phoneTips\":\"固定电话或非大陆号码\",\"phoneTag\":null,\"phoneTagList\":null},{\"phoneNumber\":\"0312-327****\",\"oriPhoneNumber\":null,\"showSource\":\"2015年报\",\"hasMoreCompany\":1,\"companyCount\":0,\"companyCountStr\":\"0\",\"companyTotalStr\":\"15\",\"phoneType\":9,\"phoneTips\":\"固定电话或非大陆号码\",\"phoneTag\":null,\"phoneTagList\":null}],\"estiblishTime\":1020787200000,\"taxBankAccount\":\"13001668808050000885\",\"regInstitute\":\"保定市市场监督管理局\",\"listedStatusType\":0,\"companyBizType\":7,\"regLocation\":\"保定市竞秀区天威西路263号\",\"regCapitalAmountUnit\":\"万人民币\",\"websiteList\":\"青山集团.com\",\"safetype\":\"unknown\",\"tagList\":[{\"color\":\"#119944\",\"background\":\"#ECF7F0\",\"layerArray\":[\"企业依法存在并继续正常运营。\"],\"sort\":10,\"title\":\"存续\",\"type\":1,\"value\":\"存续\",\"layer\":\"企业依法存在并继续正常运营。\"},{\"color\":\"#757DD3\",\"background\":\"#EDEEF9\",\"sort\":20,\"title\":\"曾用名\",\"type\":9,\"value\":\"曾用名\",\"layer\":\"保定清山房地产开发有限公司\",\"extraInfo\":[{\"name\":\"保定清山房地产开发有限公司\",\"startTime\":\"2002-05\",\"endTime\":\"2011-09\"}]},{\"color\":\"#757DD3\",\"background\":\"#EDEEF9\",\"sort\":260,\"title\":\"小微企业\",\"type\":12,\"value\":\"小微企业\",\"layer\":\"小型微型企业概念来自工信部《中小企业划型标准规定》，具体标准根据企业从业人员、营业收入、资产总额等指标，结合行业特点制定。\"},{\"color\":\"#449DE6\",\"background\":\"#E8F3FC\",\"sort\":350,\"title\":\"企业集团\",\"type\":17,\"value\":\"企业集团\",\"layer\":\"\"}],\"legalPersonId\":1765900230,\"complexName\":\"清山房地产开发有限公司\",\"companyProfileRichText\":[{\"title\":\"基本信息\",\"content\":\"清山房地产开发有限公司（曾用名：保定清山房地产开发有限公司），成立于2002年，位于河北省保定市，是一家以从事\\u003ca href='https://www.tianyancha.com/advance/search/e-pc_homeicon'\\u003e房地产业\\u003c/a\\u003e为主的企业。企业\\u003ca href='https://www.tianyancha.com/company/29459282#baseInfo'\\u003e注册资本\\u003c/a\\u003e80000万人民币，\\u003ca href='https://www.tianyancha.com/company/29459282#baseInfo'\\u003e实缴资本\\u003c/a\\u003e72300万人民币。\"},{\"title\":\"营运状况\",\"content\":\"通过天眼查大数据分析，清山房地产开发有限公司共\\u003ca href='https://www.tianyancha.com/company/29459282#inverst'\\u003e对外投资\\u003c/a\\u003e了15家企业，参与\\u003ca href='https://www.tianyancha.com/company/29459282/jingzhuang#bid'\\u003e招投标\\u003c/a\\u003e项目11次；知识产权方面有\\u003ca href='https://www.tianyancha.com/company/29459282/zhishi#tm'\\u003e商标信息\\u003c/a\\u003e60条，\\u003ca href='https://www.tianyancha.com/company/29459282/zhishi#copyrightWorks'\\u003e著作权信息\\u003c/a\\u003e1条；此外企业还拥有\\u003ca href='https://www.tianyancha.com/company/29459282/jingzhuang#mergeLicense'\\u003e行政许可\\u003c/a\\u003e12个。\"},{\"title\":\"风险概览\",\"content\":\"风险方面共发现企业有\\u003ca href='https://www.tianyancha.com/company/29459282/sifa#lawsuitWithLabel'\\u003e法律诉讼\\u003c/a\\u003e63条，涉案总金额3817.103627万元；\\u003ca href='https://www.tianyancha.com/company/29459282/sifa#announcement'\\u003e开庭公告\\u003c/a\\u003e21条，\\u003ca href='https://www.tianyancha.com/company/29459282/sifa#courtRegister'\\u003e立案信息\\u003c/a\\u003e1条。\"}],\"legalInfo\":{\"name\":\"于强\",\"hid\":1765900230,\"headUrl\":null,\"introduction\":null,\"event\":null,\"bossCertificate\":-1,\"companyNum\":31,\"office\":[{\"area\":\"河北\",\"total\":24,\"companyName\":\"保定市金马房地产开发有限公司第一分公司\",\"cid\":459953861,\"score\":0,\"state\":null},{\"area\":\"北京\",\"total\":5,\"companyName\":\"北京得客宴客科技中心（有限合伙）\",\"cid\":4011118933,\"score\":0,\"state\":null},{\"area\":\"其他\",\"total\":2,\"companyName\":\"福建省正清和茶产业有限公司\",\"cid\":2369184612,\"score\":0,\"state\":null}],\"companys\":null,\"partnerNum\":0,\"coopCount\":0,\"partners\":null,\"cid\":459953861,\"typeJoin\":null,\"alias\":null,\"serviceType\":1,\"serviceCount\":23,\"officeV1\":[{\"area\":\"河北\",\"total\":19,\"companyName\":\"保定市金马房地产开发有限公司第一分公司\",\"cid\":459953861,\"score\":0,\"state\":null},{\"area\":\"北京\",\"total\":3,\"companyName\":\"北京得客宴客科技中心（有限合伙）\",\"cid\":4011118933,\"score\":0,\"state\":null},{\"area\":\"其他\",\"total\":1,\"companyName\":\"福建省正清和茶产业有限公司\",\"cid\":2369184612,\"score\":0,\"state\":null}],\"pid\":null,\"role\":null},\"updatetime\":1683541267000,\"base\":\"heb\"},\"params\":{\"_\":\"1683768186137\",\"id\":\"29459282\"}},\"dataUpdateCount\":1,\"dataUpdatedAt\":1683768186259,\"error\":null,\"errorUpdateCount\":0,\"errorUpdatedAt\":0,\"fetchFailureCount\":0,\"fetchMeta\":null,\"isFetching\":false,\"isInvalidated\":false,\"isPaused\":false,\"status\":\"success\"},\"queryKey\":[\"/cloud-other-information/companyinfo/baseinfo/web\",{\"id\":\"29459282\"}],\"queryHash\":\"[\\\"/cloud-other-information/companyinfo/baseinfo/web\\\",{\\\"id\\\":\\\"29459282\\\"}]\"},{\"state\":{\"data\":{\"isLogin\":false,\"isVip\":false,\"isRed\":false,\"isAmbiguous\":false,\"params\":{\"_\":\"1683768186137\",\"id\":\"29459282\"}},\"dataUpdateCount\":1,\"dataUpdatedAt\":1683768186168,\"error\":null,\"errorUpdateCount\":0,\"errorUpdatedAt\":0,\"fetchFailureCount\":0,\"fetchMeta\":null,\"isFetching\":false,\"isInvalidated\":false,\"isPaused\":false,\"status\":\"success\"},\"queryKey\":[\"/next/web/getUserStates\",{\"id\":\"29459282\"}],\"queryHash\":\"[\\\"/next/web/getUserStates\\\",{\\\"id\\\":\\\"29459282\\\"}]\"},{\"state\":{\"data\":{\"message\":\"请先登录！\",\"state\":\"warn\",\"params\":{\"_\":\"1683768186138\"}},\"dataUpdateCount\":1,\"dataUpdatedAt\":1683768186145,\"error\":null,\"errorUpdateCount\":0,\"errorUpdatedAt\":0,\"fetchFailureCount\":0,\"fetchMeta\":null,\"isFetching\":false,\"isInvalidated\":false,\"isPaused\":false,\"status\":\"success\"},\"queryKey\":[\"/next/web/getUserInfo\",{}],\"queryHash\":\"[\\\"/next/web/getUserInfo\\\",{}]\"},{\"state\":{\"data\":{\"pastICV2Count\":1,\"companyProduct\":0,\"pastInverstCount\":5,\"supervisorCount\":0,\"corpTotalMainIndexCount\":0,\"declareFinancialCount\":0,\"lawsuitCount\":63,\"relationRiskCount\":204,\"cancellationCount\":0,\"jigouTzanli\":0,\"corpMainIndexCount\":0,\"staffCount\":4,\"cashFlowStatementCount\":0,\"holderCount\":3,\"pastLawsuitWithLabelCount\":26,\"legalBusinessAnalysisCount\":0,\"hkBalanceSheetCount\":0,\"pastEquityCount\":11,\"courtCount\":0,\"tmCount\":60,\"npoPurchaseServiceCount\":0,\"goudiCountV2\":4,\"applyJobCheckActivity\":1,\"certificateCount\":1,\"cpoyRCount\":0,\"finalHolderCount\":2,\"declareProjectCount\":0,\"peerAnalysisCount\":1,\"businessDisputesCount\":0,\"suppliesV2Count\":2,\"pastCourtRegisterNum\":25,\"mergePunishCount\":2,\"pastBankruptcyNum\":0,\"competeRiskCount\":0,\"financialClues\":409,\"investOrgsCount\":0,\"holdingCompanyNum\":0,\"deepRiskCount\":269,\"briefCancelAnnouncementsCountV2\":0,\"constructProjectCount\":0,\"corpIllegalsCount\":0,\"financeBlacklistCount\":0,\"bondCount\":0,\"privateFundsCount\":0,\"declareHolderCount\":0,\"bankruptcyCount\":0,\"issueRelatedNum\":0,\"equityRelationship\":1,\"businssGraphCount\":1,\"constructAllCount\":0,\"illegalCount\":0,\"pastZhixing\":14,\"historyLimitConsumptionCount\":0,\"corpBalanceSheetCount\":0,\"cancelRecordCount\":0,\"landTransfersCount\":0,\"spcjsacCount\":0,\"shareStructureNum\":0,\"pastIntelPropertyPledgeCount\":0,\"announcementNum\":0,\"mortgageCount\":0,\"environmentalPenaltiesCount\":0,\"directorCount\":0,\"commonTaxpayer\":2,\"pastTaxesAnnouncementCount\":0,\"hkCorpManagersCount\":0,\"orgChangeCount\":0,\"prospectusCountV2\":0,\"intellectualProperty\":0,\"cpoyRCountAll\":1,\"corpBasicInfoCount\":0,\"benefitStockHolderCount\":1,\"patentCountV4\":0,\"findNewsCount\":7,\"historyTerminationCaseCount\":0,\"importAndExportCount\":0,\"dishonest\":0,\"hkCorpAnnouncementsCount\":0,\"topTenNum\":0,\"holderPenetrateCount\":1,\"hkCorpMainIndexCount\":0,\"incomeStatementCount\":0,\"finalInvestCount\":20,\"hkSecBasicInfoCount\":0,\"companyRongzi\":0,\"equityCount\":4,\"landMortgagesCountV1\":0,\"checkCountV2\":1,\"laborArbitCount\":0,\"debtAndCreditCount\":12,\"historyIcpListCount\":0,\"hkIncomeStatementCount\":0,\"annualCheckCount\":0,\"hkCorpBasicInfoCount\":0,\"companyJingpin\":0,\"rankListCount\":0,\"lawTeamMembersCount\":0,\"equityChangeNum\":0,\"pastLandMortgagesCount\":3,\"endCaseCount\":0,\"zhixing\":18,\"weChatCount\":0,\"parentCompanyInfoCount\":0,\"constructChangeCount\":0,\"lawFirmCaseCount\":0,\"weiboCount\":0,\"changeCount\":40,\"ownAuthCertificateCount\":0,\"lawFirmCustomerCount\":0,\"constructQualificationCount\":0,\"holderStructureCount\":1,\"establishCount\":0,\"hkCorpDirectorsCount\":0,\"secBasicInfoCount\":0,\"honoraryCount\":0,\"financeYearCount\":0,\"projectCount\":0,\"hkCorpCashFlowCount\":0,\"judicialSaleCountV2\":0,\"hkMainIndicatorsCount\":0,\"pastMortgageCount\":0,\"landPublicitysV2Count\":0,\"stockNum\":0,\"hkStockSharesCount\":0,\"judicialCaseCount\":162,\"volatilityNum\":0,\"historyAbnormalListCount\":0,\"companyVerbCount\":7,\"assetTransactionTotal\":0,\"publicnotice\":0,\"coreThemeCount\":0,\"abnormalCount\":0,\"consumptionRestrictionCount\":1,\"constructBlackCount\":0,\"bidCount\":11,\"companyTeammember\":0,\"pastOfficerCount\":3,\"productinfo\":0,\"proxyPeersStatus\":1,\"corpGuaranteesCount\":0,\"ownHonourCount\":0,\"pastStaffMirrorCount\":7,\"productRecallCount\":0,\"historyRiskCount\":35,\"selfRiskCount\":234,\"courtRegisterCount\":1,\"financeCount\":0,\"pastJudicialaAidCount\":0,\"twManagerCount\":0,\"twStaffCount\":0,\"realControllerGraph\":1,\"govSummonCount\":0,\"judicialaAidCount\":0,\"corpCashFlowCount\":0,\"allotmentNum\":0,\"sendAnnouncementsCount\":0,\"inquiryEvaluationTotalCount\":0,\"declareBasicInfoCount\":0,\"constructGoodConductCount\":0,\"npoAppraisalQualificationCount\":0,\"competitionRiskCount\":0,\"mergeLicenseCount\":12,\"baipinCount\":89,\"pastLegalPersonCount\":1,\"trademarkDocCount\":0,\"icpCount\":2,\"mergePastLicenseCount\":7,\"manageDisPuteCount\":0,\"lawsuitWithLabelCount\":63,\"taxContraventionsCount\":0,\"ownBusinessOpportunityCount\":0,\"pastEnvironmentNum\":0,\"cooperateRiskCount\":2,\"litigationRelatedCount\":83,\"bonusInfoNum\":0,\"ownAnnouncementCount\":0,\"hkCorpSupervisorsCount\":0,\"clearingCount\":0,\"constructBadConductCount\":0,\"pastCompanySendNoticeNum\":0,\"inverstCount\":15,\"mainIndicatorsCount\":0,\"pastAnnouncementCount\":74,\"doubleRandomCheckCount\":0,\"seniorExecutiveNum\":0,\"pastDishonest\":1,\"financialAnalysis\":0,\"warningRiskCount\":133,\"corpContactInfoCount\":0,\"creditRating\":0,\"licenseV2Count\":12,\"announcementReportCount\":1,\"clientsV2Count\":0,\"historyShareholderMirror\":10,\"comDebtCount\":12,\"teleCommunicationLicense\":0,\"hkCorpTotalMainIndexCount\":0,\"constructHumanCount\":0,\"npoLegalPersonCount\":0,\"govNoticeCount\":0,\"declareInfoCount\":0,\"pastCourtCount\":2,\"evaluationCount\":0,\"ownTaxCount\":0,\"twBranchCount\":0,\"pastTmCount\":0,\"constructPunishCount\":0,\"guarantyInsuranceCount\":0,\"copyrightWorks\":1,\"corpProfitCount\":0,\"hkCashFlowStatementCount\":0,\"balanceSheetCount\":0,\"announcementCount\":21,\"branchCount\":5,\"taxCreditCount\":1,\"cRestrictedOutboundCount\":0,\"hkCorpStockHolderCount\":0,\"hkCorpProfitCount\":0,\"hkCorpBalanceSheetCount\":0,\"ownMemberCount\":0,\"equityPledgeTotalCount\":0,\"pastIllegalNum\":0,\"tenTradableNum\":0,\"pastHolderCount\":1,\"livebroadCount\":0,\"mergePastPunishCount\":9,\"franchiseCount\":0,\"patentCount\":0,\"reportCount\":9,\"standardCount\":0,\"isAllCountV3\":1},\"dataUpdateCount\":1,\"dataUpdatedAt\":1683768186259,\"error\":null,\"errorUpdateCount\":0,\"errorUpdatedAt\":0,\"fetchFailureCount\":0,\"fetchMeta\":null,\"isFetching\":false,\"isInvalidated\":false,\"isPaused\":false,\"status\":\"success\"},\"queryKey\":\"base_count\",\"queryHash\":\"[\\\"base_count\\\"]\"},{\"state\":{\"data\":{\"state\":\"ok\",\"message\":\"\",\"special\":\"\",\"vipMessage\":\"\",\"isLogin\":0,\"errorCode\":0,\"data\":{\"stockType\":0,\"stockType2\":0},\"params\":{\"_\":\"1683768186138\",\"gid\":\"29459282\",\"stockType\":\"\"}},\"dataUpdateCount\":1,\"dataUpdatedAt\":1683768186157,\"error\":null,\"errorUpdateCount\":0,\"errorUpdatedAt\":0,\"fetchFailureCount\":0,\"fetchMeta\":null,\"isFetching\":false,\"isInvalidated\":false,\"isPaused\":false,\"status\":\"success\"},\"queryKey\":[\"/cloud-listed-company/listed/count\",{\"gid\":\"29459282\",\"stockType\":\"\"}],\"queryHash\":\"[\\\"/cloud-listed-company/listed/count\\\",{\\\"gid\\\":\\\"29459282\\\",\\\"stockType\\\":\\\"\\\"}]\"},{\"state\":{\"data\":{\"showCompany\":true,\"showRisk\":{\"supplierDefendantCount\":\"0\",\"supplierPlaintiffCount\":\"0\",\"clientDefendantCount\":\"0\",\"clientPlaintiffCount\":\"0\",\"cooperationScore\":\"4.8\",\"supplierCaseCount\":\"0\",\"clientCaseCount\":\"0\",\"shadeMark\":\"mustlogin\"},\"showPeer\":true},\"dataUpdateCount\":1,\"dataUpdatedAt\":1683768186180,\"error\":null,\"errorUpdateCount\":0,\"errorUpdatedAt\":0,\"fetchFailureCount\":0,\"fetchMeta\":null,\"isFetching\":false,\"isInvalidated\":false,\"isPaused\":false,\"status\":\"success\"},\"queryKey\":[\"nav_exist_info\",\"29459282\"],\"queryHash\":\"[\\\"nav_exist_info\\\",\\\"29459282\\\"]\"},{\"state\":{\"data\":{\"state\":\"ok\",\"message\":\"\",\"special\":\"\",\"vipMessage\":\"\",\"isLogin\":0,\"data\":{\"authStatus\":-1,\"materialStatus\":0,\"expiredStatus\":0,\"gray\":false},\"params\":{\"_\":\"1683768186138\",\"graphId\":\"29459282\"}},\"dataUpdateCount\":1,\"dataUpdatedAt\":1683768186156,\"error\":null,\"errorUpdateCount\":0,\"errorUpdatedAt\":0,\"fetchFailureCount\":0,\"fetchMeta\":null,\"isFetching\":false,\"isInvalidated\":false,\"isPaused\":false,\"status\":\"success\"},\"queryKey\":[\"/services/v3/claim/year/check/company\",{\"graphId\":\"29459282\"}],\"queryHash\":\"[\\\"/services/v3/claim/year/check/company\\\",{\\\"graphId\\\":\\\"29459282\\\"}]\"},{\"state\":{\"data\":{\"state\":\"warn\",\"message\":\"登录后可查看更多\",\"special\":\"mustlogin\",\"vipMessage\":\"\",\"isLogin\":0,\"data\":null,\"params\":{\"_\":\"1683768186138\",\"type\":1,\"gid\":\"29459282\"}},\"dataUpdateCount\":1,\"dataUpdatedAt\":1683768186148,\"error\":null,\"errorUpdateCount\":0,\"errorUpdatedAt\":0,\"fetchFailureCount\":0,\"fetchMeta\":null,\"isFetching\":false,\"isInvalidated\":false,\"isPaused\":false,\"status\":\"success\"},\"queryKey\":[\"/cloud-monitor-provider/v4/monitor/checkMonitor.json\",{\"type\":1,\"gid\":\"29459282\"}],\"queryHash\":\"[\\\"/cloud-monitor-provider/v4/monitor/checkMonitor.json\\\",{\\\"gid\\\":\\\"29459282\\\",\\\"type\\\":1}]\"},{\"state\":{\"data\":{\"state\":\"ok\",\"message\":\"\",\"special\":\"\",\"vipMessage\":\"\",\"isLogin\":0,\"data\":{\"brandInfo\":{\"brandStatus\":0},\"investAgencyInfo\":{\"investAgencyStatus\":0},\"hedgeFundInfo\":{\"hedgeFundStatus\":0}},\"httpStatus\":200,\"params\":{\"_\":\"1683768186138\",\"cid\":\"29459282\"}},\"dataUpdateCount\":1,\"dataUpdatedAt\":1683768186172,\"error\":null,\"errorUpdateCount\":0,\"errorUpdatedAt\":0,\"fetchFailureCount\":0,\"fetchMeta\":null,\"isFetching\":false,\"isInvalidated\":false,\"isPaused\":false,\"status\":\"success\"},\"queryKey\":[\"/next/web/company/getTagInfo\",{\"cid\":\"29459282\"}],\"queryHash\":\"[\\\"/next/web/company/getTagInfo\\\",{\\\"cid\\\":\\\"29459282\\\"}]\"},{\"state\":{\"data\":{\"state\":\"ok\",\"message\":\"\",\"special\":\"\",\"data\":{\"gid\":29459282,\"name\":\"清山房地产开发有限公司\",\"groupRename\":\"清山\",\"groupUUID\":\"cfe655151f3644acb302d47390795090\",\"actualControlPersonGid\":1765900230,\"actualControlPersonCompanyGid\":29459282,\"actualControlPersonName\":\"于强\",\"actualControlPersonType\":2,\"groupLogo\":\"https://img5.tianyancha.com/logo/lll/115612fda69f56c2cd3cb4522b23a047.png@!f_200x200\",\"alias\":\"清山\",\"groupNum\":22,\"coreNum\":12,\"listedNum\":0,\"groupType\":0,\"actualControlPersonList\":[{\"type\":2,\"hgid\":1765900230,\"cgid\":29459282,\"name\":\"于强\"}]},\"code\":1,\"params\":{\"_\":\"1683768186138\",\"gid\":\"29459282\"}},\"dataUpdateCount\":1,\"dataUpdatedAt\":1683768186149,\"error\":null,\"errorUpdateCount\":0,\"errorUpdatedAt\":0,\"fetchFailureCount\":0,\"fetchMeta\":null,\"isFetching\":false,\"isInvalidated\":false,\"isPaused\":false,\"status\":\"success\"},\"queryKey\":[\"/cloud-company-group/companyGroup/baseInfo\",{\"gid\":\"29459282\"}],\"queryHash\":\"[\\\"/cloud-company-group/companyGroup/baseInfo\\\",{\\\"gid\\\":\\\"29459282\\\"}]\"},{\"state\":{\"data\":{\"state\":\"ok\",\"message\":\"\",\"special\":\"\",\"vipMessage\":\"\",\"isLogin\":0,\"data\":[{\"date\":\"2023-05-04\",\"name\":\"被列入被执行人名单\"},{\"date\":\"2023-04-20\",\"name\":\"新增法律诉讼\"},{\"date\":\"2023-04-15\",\"name\":\"新增法律诉讼\"}],\"params\":{\"_\":\"1683768186138\",\"gid\":\"29459282\"}},\"dataUpdateCount\":1,\"dataUpdatedAt\":1683768186194,\"error\":null,\"errorUpdateCount\":0,\"errorUpdatedAt\":0,\"fetchFailureCount\":0,\"fetchMeta\":null,\"isFetching\":false,\"isInvalidated\":false,\"isPaused\":false,\"status\":\"success\"},\"queryKey\":[\"/cloud-cia-provider/cia/carousel\",{\"gid\":\"29459282\"}],\"queryHash\":\"[\\\"/cloud-cia-provider/cia/carousel\\\",{\\\"gid\\\":\\\"29459282\\\"}]\"},{\"state\":{\"data\":{\"state\":\"ok\",\"message\":\"\",\"special\":\"\",\"vipMessage\":\"\",\"isLogin\":0,\"errorCode\":0,\"data\":{\"riskLevel\":\"\",\"list\":[{\"count\":234,\"name\":\"自身风险\",\"otherCount\":233,\"list\":[{\"orderNum\":0,\"id\":514489473,\"riskCount\":1,\"title\":\"该公司\\u003cem\\u003e*******\\u003c/em\\u003e\",\"type\":32,\"desc\":\"限制消费令\"}],\"type\":1},{\"count\":204,\"name\":\"周边风险\",\"otherCount\":201,\"list\":[{\"cname\":\"保定国瑞房地产开发有限公司\",\"orderNum\":10,\"id\":508335369,\"graphId\":2337668423,\"riskCount\":3,\"title\":\"该公司投资的\\u003cem\\u003e************\\u003c/em\\u003e是\\u003cem\\u003e************\\u003c/em\\u003e\",\"type\":3,\"desc\":\"失信被执行人\"}],\"type\":2},{\"count\":35,\"name\":\"历史风险\",\"otherCount\":34,\"list\":[{\"orderNum\":0,\"id\":323990417,\"riskCount\":1,\"title\":\"该公司是\\u003cem\\u003e************\\u003c/em\\u003e\",\"type\":71,\"desc\":\"失信被执行人_历史\"}],\"type\":3},{\"count\":133,\"name\":\"预警提醒\",\"otherCount\":131,\"list\":[{\"orderNum\":0,\"id\":55911873,\"riskCount\":2,\"title\":\"该公司\\u003cem\\u003e*******\\u003c/em\\u003e\",\"type\":15,\"desc\":\"法定代表人变更\"}],\"type\":0}],\"riskLevelColor\":\"\"},\"params\":{\"_\":\"1683768186138\",\"gid\":\"29459282\"}},\"dataUpdateCount\":1,\"dataUpdatedAt\":1683768186152,\"error\":null,\"errorUpdateCount\":0,\"errorUpdatedAt\":0,\"fetchFailureCount\":0,\"fetchMeta\":null,\"isFetching\":false,\"isInvalidated\":false,\"isPaused\":false,\"status\":\"success\"},\"queryKey\":[\"/cloud-other-information/risk/companyRiskPanel\",{\"gid\":\"29459282\"}],\"queryHash\":\"[\\\"/cloud-other-information/risk/companyRiskPanel\\\",{\\\"gid\\\":\\\"29459282\\\"}]\"},{\"state\":{\"data\":{\"state\":\"ok\",\"message\":\"\",\"special\":\"\",\"vipMessage\":\"\",\"isLogin\":0,\"errorCode\":0,\"data\":{\"total\":3,\"list\":[{\"labelName\":\"客户\",\"count\":1,\"partnerType\":2,\"items\":[{\"companyName\":\"石家庄中泰华银房地产开发有限公司\",\"logo\":\"https://img5.tianyancha.com/logo/lll/651f82eb760e5e444f783640e026511f.png@!f_200x200\",\"companyGid\":2351803965,\"isCooperatedInYear\":0,\"onlineStatus\":1,\"type\":2,\"alias\":\"中泰华银\",\"pricingPackage\":0,\"cooperatedTime\":0}]},{\"labelName\":\"供应商\",\"count\":2,\"partnerType\":1,\"items\":[{\"companyName\":\"河北民安工程建设监理有限公司\",\"companyGid\":492374851,\"isCooperatedInYear\":0,\"onlineStatus\":1,\"type\":1,\"alias\":\"民安\",\"pricingPackage\":0,\"cooperatedTime\":0},{\"companyName\":\"河北省青山建设集团有限公司\",\"companyGid\":1048268149,\"isCooperatedInYear\":0,\"onlineStatus\":1,\"type\":1,\"alias\":\"青山建设\",\"pricingPackage\":0,\"cooperatedTime\":0}]}],\"currentUserState\":0,\"isClaimedCompany\":0},\"params\":{\"gid\":\"29459282\",\"pageNum\":1,\"pageSize\":15}},\"dataUpdateCount\":1,\"dataUpdatedAt\":1683768186157,\"error\":null,\"errorUpdateCount\":0,\"errorUpdatedAt\":0,\"fetchFailureCount\":0,\"fetchMeta\":null,\"isFetching\":false,\"isInvalidated\":false,\"isPaused\":false,\"status\":\"success\"},\"queryKey\":[\"/ads-dispatch/partner/headPart\",{\"gid\":\"29459282\",\"pageNum\":1,\"pageSize\":15}],\"queryHash\":\"[\\\"/ads-dispatch/partner/headPart\\\",{\\\"gid\\\":\\\"29459282\\\",\\\"pageNum\\\":1,\\\"pageSize\\\":15}]\"},{\"state\":{\"data\":{\"state\":\"ok\",\"message\":\"\",\"special\":\"\",\"vipMessage\":\"\",\"isLogin\":0,\"errorCode\":0,\"data\":{},\"params\":{\"_\":\"1683768186138\",\"gid\":\"29459282\",\"pageNum\":1,\"pageSize\":20}},\"dataUpdateCount\":1,\"dataUpdatedAt\":1683768186150,\"error\":null,\"errorUpdateCount\":0,\"errorUpdatedAt\":0,\"fetchFailureCount\":0,\"fetchMeta\":null,\"isFetching\":false,\"isInvalidated\":false,\"isPaused\":false,\"status\":\"success\"},\"queryKey\":[\"/cloud-other-information/autonomousInfo/corporateServicesV2\",{\"gid\":\"29459282\",\"pageNum\":1,\"pageSize\":20}],\"queryHash\":\"[\\\"/cloud-other-information/autonomousInfo/corporateServicesV2\\\",{\\\"gid\\\":\\\"29459282\\\",\\\"pageNum\\\":1,\\\"pageSize\\\":20}]\"},{\"state\":{\"data\":{\"data\":{\"companyPT\":{\"tdkIdentify\":\"companyPT\",\"name\":\"公司详情页-普通公司\",\"title\":\"$companyName - 天眼查\",\"description\":\"天眼查为您提供$companyName的企业信息查询服务，查询$companyName工商注册信息、公司电话、公司地址、公司邮箱网址、公司经营风险、公司发展状况、公司财务状况、公司股东法人高管、商标、融资、专利、法律诉讼等$companyName企业信用信息，想了解$companyName怎么样，就上天眼查。\",\"keywords\":\"$companyName,$companyName地址,$companyName怎么样,$companyName电话\"},\"time\":\"5/11/2023, 9:02:57 AM\"},\"state\":\"ok\",\"params\":{\"_\":\"1683768186259\",\"type\":\"companyPT\"}},\"dataUpdateCount\":1,\"dataUpdatedAt\":1683768186262,\"error\":null,\"errorUpdateCount\":0,\"errorUpdatedAt\":0,\"fetchFailureCount\":0,\"fetchMeta\":null,\"isFetching\":false,\"isInvalidated\":false,\"isPaused\":false,\"status\":\"success\"},\"queryKey\":[\"/next/web/seo/tdkConfig\",{\"type\":\"companyPT\"}],\"queryHash\":\"[\\\"/next/web/seo/tdkConfig\\\",{\\\"type\\\":\\\"companyPT\\\"}]\"}]},\"_sentryTraceData\":\"cf7977e75d9345baa1954e11514fad79-a36b1d706eef30df-0\",\"_sentryBaggage\":\"sentry-environment=production,sentry-release=02690b86-10212414-prod,sentry-transaction=%2Fcompany%2F%5Bcid%5D%2Findexx,sentry-public_key=1113296606404dc6bd3f1d6ba1dcec76,sentry-trace_id=cf7977e75d9345baa1954e11514fad79,sentry-sample_rate=0.01\"},\"isSEO\":false,\"__N_SSP\":true},\"page\":\"/company/[cid]\",\"query\":{\"cid\":\"29459282\"},\"buildId\":\"02690b86-10212414-prod\",\"assetPrefix\":\"https://tyc-fe-cdn.tianyancha.com/tyc-web-next\",\"runtimeConfig\":{\"sensors\":{\"sensorsServerUrl\":\"https://sensorsapi.tianyancha.com/sa?project=production\",\"sensorsDebug\":false},\"sentry\":{\"dsn\":\"https://1113296606404dc6bd3f1d6ba1dcec76@sentry-prod.jindidata.com/6\",\"tracesSampleRate\":0.01},\"logger\":{\"clsOrigin\":\"https://cls.tianyancha.com\"},\"jeager\":{\"endpoint\":\"http://jaeger-collector.services.huawei/\"},\"zipkin\":{\"url\":\"http://zipkin.tyc.io/\",\"samplerRatio\":0.001},\"xtransit\":{\"server\":\"ws://inner.xtransit.fronttyc.io:8643\",\"appId\":5,\"appSecret\":\"58023739522778965e4483284530938c\"},\"subDomainSuffix\":\".tianyancha.com\",\"serverHttp\":\"https://\",\"serverDomain\":\"https://www.tianyancha.com\",\"qifuLoginDomain\":\"https://pcbff.tianyanqifu.com\",\"qifuServerDomain\":\"https://www.tianyanqifu.com\",\"disServerDomain\":\"https://dis.tianyancha.com\",\"newsServerDomain\":\"https://news.tianyancha.com\",\"wapServerDomain\":\"https://m.tianyancha.com\",\"discussServerDomain\":\"https://discuss.tianyancha.com\",\"env\":\"production\"},\"isFallback\":false,\"gssp\":true,\"customServer\":true,\"appGip\":true,\"scriptLoader\":[]}\n" +
            "\t\t\t</script>";
    String startText = "<script id=\"__NEXT_DATA__\" type=\"application/json\">";
    String endText = "</script>";
    String data = s.substring(s.lastIndexOf(startText)+startText.length(),s.lastIndexOf(endText));
    System.out.println(data);
    Map map = JsonUtils.toObject(data, Map.class);
    Map props = (Map)map.get("props");
    Map pageProps = (Map)props.get("pageProps");
    Map dehydratedState = (Map)pageProps.get("dehydratedState");
    Map queries = ((List<Map>)dehydratedState.get("queries")).get(0);
    Map state = (Map)queries.get("state");
    Map data1 = (Map)state.get("data");
    Map data2 = (Map)data1.get("data");
    String historyNames = (String)data2.get("historyNames");
    String enName = (String)data2.get("property3");
    System.out.println(historyNames);
    System.out.println(enName);
}
}