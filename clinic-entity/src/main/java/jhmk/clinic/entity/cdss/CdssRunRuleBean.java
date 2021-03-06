package jhmk.clinic.entity.cdss;

import java.util.List;
import java.util.Map;

public class CdssRunRuleBean {


    private String id;
    private String doctor_id;
    private String doctor_name;
    private String dept_code;
    private String patient_id;
    private String warnSource;//住院 门诊
    private String visit_id;
    private String pageSource;
    private Map<String, String> binganshouye;
    private Map<String, String> physicalSign;
    private List<Map<String, String>> binglizhenduan;
    private List<Map<String, String>> shouyezhenduan;
    private Map<String, String> ruyuanjilu;
    //    private List<Map<String, List<Map<String, String>>>> jianyanbaogao;
    private List<Map<String, String>> jianyanbaogao;
    private List<Map<String, String>> jianchabaogao;
    private List<Map<String, String>> yizhu;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getDept_code() {
        return dept_code;
    }

    public void setDept_code(String dept_code) {
        this.dept_code = dept_code;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getWarnSource() {
        return warnSource;
    }

    public void setWarnSource(String warnSource) {
        this.warnSource = warnSource;
    }

    public String getVisit_id() {
        return visit_id;
    }

    public void setVisit_id(String visit_id) {
        this.visit_id = visit_id;
    }

    public String getPageSource() {
        return pageSource;
    }

    public void setPageSource(String pageSource) {
        this.pageSource = pageSource;
    }

    public Map<String, String> getBinganshouye() {
        return binganshouye;
    }

    public void setBinganshouye(Map<String, String> binganshouye) {
        this.binganshouye = binganshouye;
    }

    public Map<String, String> getPhysicalSign() {
        return physicalSign;
    }

    public void setPhysicalSign(Map<String, String> physicalSign) {
        this.physicalSign = physicalSign;
    }

    public List<Map<String, String>> getBinglizhenduan() {
        return binglizhenduan;
    }

    public void setBinglizhenduan(List<Map<String, String>> binglizhenduan) {
        this.binglizhenduan = binglizhenduan;
    }

    public List<Map<String, String>> getShouyezhenduan() {
        return shouyezhenduan;
    }

    public void setShouyezhenduan(List<Map<String, String>> shouyezhenduan) {
        this.shouyezhenduan = shouyezhenduan;
    }

    public Map<String, String> getRuyuanjilu() {
        return ruyuanjilu;
    }

    public void setRuyuanjilu(Map<String, String> ruyuanjilu) {
        this.ruyuanjilu = ruyuanjilu;
    }

    public List<Map<String, String>> getJianyanbaogao() {
        return jianyanbaogao;
    }

    public void setJianyanbaogao(List<Map<String, String>> jianyanbaogao) {
        this.jianyanbaogao = jianyanbaogao;
    }

    public List<Map<String, String>> getJianchabaogao() {
        return jianchabaogao;
    }

    public void setJianchabaogao(List<Map<String, String>> jianchabaogao) {
        this.jianchabaogao = jianchabaogao;
    }

    public List<Map<String, String>> getYizhu() {
        return yizhu;
    }

    public void setYizhu(List<Map<String, String>> yizhu) {
        this.yizhu = yizhu;
    }
}