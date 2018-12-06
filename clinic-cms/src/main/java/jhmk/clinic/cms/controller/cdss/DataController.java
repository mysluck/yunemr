package jhmk.clinic.cms.controller.cdss;

import com.alibaba.fastjson.JSONObject;
import jhmk.clinic.cms.SamilarService;
import jhmk.clinic.cms.controller.ruleService.BasyService;
import jhmk.clinic.cms.controller.ruleService.RyjuService;
import jhmk.clinic.cms.controller.ruleService.SyzdService;
import jhmk.clinic.cms.service.CdssService;
import jhmk.clinic.cms.service.ReadFileService;
import jhmk.clinic.core.base.BaseController;
import jhmk.clinic.core.config.CdssConstans;
import jhmk.clinic.core.util.CompareUtil;
import jhmk.clinic.core.util.HttpClient;
import jhmk.clinic.entity.bean.Misdiagnosis;
import jhmk.clinic.entity.cdss.CdssDiffBean;
import jhmk.clinic.entity.cdss.CdssRuleBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * @author ziyu.zhou
 * @date 2018/7/31 14:42
 */
//获取测试数据相关
@Controller
@RequestMapping("/test/data")
public class DataController extends BaseController {
    Logger logger = LoggerFactory.getLogger(DataController.class);

    @Autowired
    SamilarService samilarService;
    @Autowired
    BasyService basyService;
    @Autowired
    RyjuService ryjuService;
    @Autowired
    SyzdService syzdService;
    @Autowired
    CdssService cdssService;

    @RequestMapping("/getGukedata")
    @ResponseBody
    public void getGukedata(HttpServletResponse response) {
        List<Misdiagnosis> gukeData = basyService.getGukeData();
        Set<Misdiagnosis> saveData = new HashSet<>();
        for (Misdiagnosis bean : gukeData) {
            String id = bean.getId();
            //获取既往史存在疾病
            Misdiagnosis jwSdieases = ryjuService.getJWSdieases(id);
            if (jwSdieases == null || jwSdieases.getHisDiseaseList() == null || jwSdieases.getHisDiseaseList().size() == 0) {
                continue;
            }
            //获取出院诊断集合
            List<String> syzd = syzdService.getDiseaseList(id);
            List<String> hisDiseaseList = jwSdieases.getHisDiseaseList();
            //遍历既往史存在疾病 如果首页诊断不存在 则保存
            for (String s : hisDiseaseList) {
                if (!syzd.contains(s)) {
                    bean.setNowDiseaseList(hisDiseaseList);
                    saveData.add(bean);
                    continue;
                }
            }

        }
        BufferedWriter bufferedWriter = null;
//        File file = new File("/data/1/CDSS/3院骨科漏诊数据.txt");
        File file = new File("C:/嘉和美康文档/3院测试数据/3院骨科漏诊数据.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            for (Misdiagnosis mz : saveData) {

                bufferedWriter.write(mz.getId() + "," + mz.getPatient_id() + "," + mz.getVisit_id()
                        + mz.getDept_discharge_from_name() + ","
                        + mz.getDistrict_discharge_from_name() + ","
                        + mz.getHisDiseaseList() + ","
                        + mz.getNowDiseaseList() + ","
                        + mz.getSrc()
                );

                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        wirte(response, "导入文件成功");
    }

    @RequestMapping("/getOtolaryngologydata")
    @ResponseBody
    public void getOtolaryngologydata(HttpServletResponse response) {
        List<Misdiagnosis> gukeData = basyService.getGukeData();
        Set<Misdiagnosis> saveData = new HashSet<>();
        for (Misdiagnosis bean : gukeData) {
            String id = bean.getId();
            //获取既往史存在疾病
            Misdiagnosis jwSdieases = ryjuService.getJWSdieases(id);
            if (jwSdieases == null || jwSdieases.getHisDiseaseList() == null || jwSdieases.getHisDiseaseList().size() == 0) {
                continue;
            }
            //获取出院诊断集合
            List<String> syzd = syzdService.getDiseaseList(id);
            List<String> hisDiseaseList = jwSdieases.getHisDiseaseList();
            //遍历既往史存在疾病 如果首页诊断不存在 则保存
            for (String s : hisDiseaseList) {
                if (!syzd.contains(s)) {
                    bean.setNowDiseaseList(hisDiseaseList);
                    saveData.add(bean);
                    continue;
                }
            }

        }
        BufferedWriter bufferedWriter = null;
//        File file = new File("/data/1/CDSS/3院骨科漏诊数据.txt");
        File file = new File("C:/嘉和美康文档/3院测试数据/3院骨科漏诊数据.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            for (Misdiagnosis mz : saveData) {

                bufferedWriter.write(mz.getId() + "," + mz.getPatient_id() + "," + mz.getVisit_id()
                        + mz.getDept_discharge_from_name() + ","
                        + mz.getDistrict_discharge_from_name() + ","
                        + mz.getHisDiseaseList() + ","
                        + mz.getNowDiseaseList() + ","
                        + mz.getSrc()
                );

                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        wirte(response, "导入文件成功");
    }


    /**
     * 根据条件获取确诊时间
     *
     * @param response
     */
    @RequestMapping("/getAvgDateByCondition")
    @ResponseBody
    public void getAvgDateByCondition(HttpServletResponse response, @RequestBody String map) {
        JSONObject jsonObject = JSONObject.parseObject(map);
        String deptName = jsonObject.getString("deptName");
        String startTime = jsonObject.getString("startTime");
        String endTime = jsonObject.getString("endTime");
        String diseaseName = jsonObject.getString("diseaseName");
        int page = jsonObject.getInteger("page") == null ? 1 : jsonObject.getInteger("page");
        int pageSize = jsonObject.getInteger("pageSize") == null ? 20 : jsonObject.getInteger("page");

        String jsonStr = cdssService.getJsonStr(deptName, startTime, endTime, page, pageSize);
        logger.info("条件信息：{}", jsonStr);
        String s = HttpClient.doPost(CdssConstans.patients, jsonStr);
        logger.info("结果信息：{}", s);
        List<CdssDiffBean> diffBeanList = cdssService.getDiffBeanList(s);
//        List<CdssDiffBean> diffBeanList1 = cdssService.getDiffBeanList(diffBeanList);
        List<CdssDiffBean> allDiffBeanList = cdssService.getDiffBean(diffBeanList);
        List<CdssDiffBean> resultList = new LinkedList<>();
        if (StringUtils.isNotBlank(diseaseName)) {
            for (CdssDiffBean bean : allDiffBeanList) {
                if (diseaseName.contains(bean.getChuyuanzhenduan()) || bean.getChuyuanzhenduan().contains(diseaseName)) {
                    resultList.add(bean);
                }
            }
        } else {
            resultList = allDiffBeanList;
        }
        Map<Integer, Integer> stringStatisticsBeanMap = cdssService.analyzeData2CountAndAvgDay(resultList);
        System.out.println(allDiffBeanList);
//        Write2File.w2fileList(allDiffBeanList, "2017年呼吸科确诊数据.txt");

        wirte(response, stringStatisticsBeanMap);
    }

    /**
     * 对比下这些医生ID，2018年7月-11月，初诊正确率有没有改变
     */
    @RequestMapping("/analyzeData20181206")
    public void analyzeData20181206(HttpServletResponse response, @RequestBody(required = false) String map) {
        JSONObject jsonObject = JSONObject.parseObject(map);
//        String startTime = jsonObject.getString("startTime");
//        String endTime = jsonObject.getString("endTime");
        List<String> list = ReadFileService.readSourceList("20181206doctorId");
        List<CdssRuleBean> resultList = new ArrayList<>();
        for (String id : list) {
            List<CdssRuleBean> beanByDoctorIdAndDate = basyService.getBeanByDoctorIdAndDate(id, "2018-07-01 00:00:00", "2018-12-01 00:00:00");
            resultList.addAll(beanByDoctorIdAndDate);
        }
        Collections.sort(resultList, CompareUtil.createComparator(1, "doctor_id"));
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("主表");
        String fileName = "20181206data" + ".xls";//设置要导出的文件的名字 //新增数据行，并且设置单元格数据
        int rowNum = 1;
        String[] headers = {"入院时间", "PID", "VID", "DoctorId", "DoctorName", "入院主诊断", "出院主诊断"}; //headers表示excel表中第一行的表头
        HSSFRow row = sheet.createRow(0);
        //在excel表中添加表头
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        } //在表中存放查询到的数据放入对应的列
        for (CdssRuleBean bean : resultList) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(bean.getAdmission_time());
            row1.createCell(1).setCellValue(bean.getPatient_id());
            row1.createCell(2).setCellValue(bean.getVisit_id());
            row1.createCell(3).setCellValue(bean.getDoctor_id());
            row1.createCell(4).setCellValue(bean.getDoctor_name());
            row1.createCell(5).setCellValue(bean.getRycz());
            row1.createCell(6).setCellValue(bean.getCyzd());
            rowNum++;
        }
        try {
            FileOutputStream fos = new FileOutputStream("/data/1/CDSS/" + fileName);
//            FileOutputStream fos = new FileOutputStream("C:/嘉和美康文档/3院测试数据/"+fileName);
            workbook.write(fos);
            System.out.println("写入成功");
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        wirte(response, "写入成功");

    }

//    public void method2(List<CdssRuleBean> resultList) {
//        HSSFWorkbook workbook = new HSSFWorkbook();
//        HSSFSheet sheet = workbook.createSheet("统计表");
//        String fileName = "20181206data" + ".xls";//设置要导出的文件的名字 //新增数据行，并且设置单元格数据
//        int rowNum = 1;
//        String[] headers = {"时间", "DoctorId", "符合率"}; //headers表示excel表中第一行的表头
//        HSSFRow row = sheet.createRow(0);
//        Map<String, Date1206> map = new HashMap<>();
//        //在excel表中添加表头
//        for (CdssRuleBean bean : resultList) {
//            String doctor_id = bean.getDoctor_id();
//            String rycz = bean.getRycz();
//            String cyzd = bean.getCyzd();
//            boolean fatherAndSon = samilarService.isFatherAndSon(rycz, cyzd);
//            if (map.containsKey(doctor_id)) {
//                Date1206 date1206 = map.get(doctor_id);
//                if (fatherAndSon) {
//                    date1206.setAll(date1206.getAll() + 1);
//                    date1206.setCurrect(date1206.getCurrect() + 1);
//                } else {
//                    date1206.setError(date1206.getError() + 1);
//                    date1206.setAll(date1206.getAll() + 1);
//                }
//                map.put(doctor_id, date1206);
//            } else {
//                Date1206 date1206 = new Date1206();
//                if (fatherAndSon) {
//                    date1206.setAll(1);
//                    date1206.setCurrect(1);
//                    date1206.setError(0);
//                    date1206.setDoctorId(doctor_id);
//                } else {
//                    date1206.setAll(1);
//                    date1206.setCurrect(0);
//                    date1206.setError(1);
//                    date1206.setDoctorId(doctor_id);
//                }
//                map.put(doctor_id, date1206);
//            }
//        }
//        for (int i = 0; i < headers.length; i++) {
//            HSSFCell cell = row.createCell(i);
//            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
//            cell.setCellValue(text);
//        } //在表中存放查询到的数据放入对应的列
//        Set<Map.Entry<String, Date1206>> entries = map.entrySet();
//        for (Map.Entry<String, Date1206>  bean : map.entrySet()) {
//            HSSFRow row1 = sheet.createRow(rowNum);
//            row1.createCell(0).setCellValue(bean.getAdmission_time());
//            row1.createCell(1).setCellValue(bean.getPatient_id());
//            row1.createCell(2).setCellValue(bean.getVisit_id());
//            rowNum++;
//        }
//        try {
//            FileOutputStream fos = new FileOutputStream("/data/1/CDSS/" + fileName);
////            FileOutputStream fos = new FileOutputStream("C:/嘉和美康文档/3院测试数据/"+fileName);
//            workbook.write(fos);
//            System.out.println("写入成功");
//            fos.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public static void main(String[] args) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("信息表");
        List<CdssRuleBean> classmateList = null;
        String fileName = "20181206data" + ".xls";//设置要导出的文件的名字 //新增数据行，并且设置单元格数据
        int rowNum = 1;
        String[] headers = {"入院时间", "PID", "VID", "DoctorId", "DoctorName", "入院主诊断", "出院主诊断"}; //headers表示excel表中第一行的表头
        HSSFRow row = sheet.createRow(0);
        //在excel表中添加表头
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        } //在表中存放查询到的数据放入对应的列
        for (CdssRuleBean bean : classmateList) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(bean.getAdmission_time());
            row1.createCell(1).setCellValue(bean.getPatient_id());
            row1.createCell(2).setCellValue(bean.getVisit_id());
            row1.createCell(3).setCellValue(bean.getDoctor_id());
            row1.createCell(4).setCellValue(bean.getDoctor_name());
            row1.createCell(5).setCellValue(bean.getRycz());
            row1.createCell(6).setCellValue(bean.getCyzd());
            rowNum++;
        }


        try {
            FileOutputStream fos = new FileOutputStream("/data/1/CDSS/tcresult.xls");
            workbook.write(fos);
            System.out.println("写入成功");
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
