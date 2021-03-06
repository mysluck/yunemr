package jhmk.clinic.cms.controller.ruleService;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import jhmk.clinic.core.config.CdssConstans;
import jhmk.clinic.entity.bean.Disease;
import jhmk.clinic.entity.bean.HistoryOfPastIllness;
import jhmk.clinic.entity.bean.Misdiagnosis;
import jhmk.clinic.entity.bean.Ruyuanjilu;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.*;

import static jhmk.clinic.core.util.MongoUtils.getCollection;

/**
 * @author ziyu.zhou
 * @date 2018/7/31 13:56
 * r入院记录
 */

@Service
public class RyjuService {
    MongoCollection<Document> binganshouye = getCollection(CdssConstans.DATASOURCE, CdssConstans.BINGANSHOUYE);
    //入院记录
    static MongoCollection<Document> ruyuanjilu = getCollection(CdssConstans.DATASOURCE, CdssConstans.RUYUANJILU);


    /**
     * 根据id获取既往史
     *
     * @param id
     * @return
     */

    public Misdiagnosis getJWSdieases(String id) {
        Misdiagnosis jiwangshi = new Misdiagnosis();
        List<String> jwDiseases = new LinkedList<>();
        List<Document> countPatientId2 = Arrays.asList(
                //过滤数据
                new Document("$match", new Document("_id", id)),
                new Document("$project", new Document("patient_id", 1).append("visit_id", 1).append("ruyuanjilu.history_of_past_illness", 1)
                )

        );
        AggregateIterable<Document> output = ruyuanjilu.aggregate(countPatientId2);

        for (Document document : output) {

            Document ruyuanjilu = (Document) document.get("ruyuanjilu");
            String patient_id = document.getString("patient_id");
            String visit_id = document.getString("visit_id");

            if (ruyuanjilu == null) {
                continue;
            }

            //既往史
            Document history_of_past_illness = (Document) ruyuanjilu.get("history_of_past_illness");
            if (Objects.nonNull(history_of_past_illness)) {
                String src = history_of_past_illness.getString("src");
                if (Objects.nonNull(history_of_past_illness.get("disease"))) {
                    continue;
                }
                ArrayList<Document> array = history_of_past_illness.get("disease", ArrayList.class);
                if (Objects.nonNull(array)) {

                    for (Document doc : array) {
                        String disease_name = doc.getString("disease_name");
                        jwDiseases.add(disease_name);
                    }
                    jiwangshi.setId(id);
                    jiwangshi.setPatient_id(patient_id);
                    jiwangshi.setVisit_id(visit_id);
                    jiwangshi.setHisDiseaseList(jwDiseases);
                    jiwangshi.setSrc(src);
                }
            }
        }

        return jiwangshi;
    }

    public Ruyuanjilu getRuyuanjiluById(String id) {
        Ruyuanjilu bean = new Ruyuanjilu();
        List<String> jwDiseases = new LinkedList<>();
        List<Document> countPatientId2 = Arrays.asList(
                //过滤数据
                new Document("$match", new Document("_id", id)),
                new Document("$project", new Document("patient_id", 1).append("visit_id", 1).append("ruyuanjilu.history_of_past_illness", 1)
                )

        );
        AggregateIterable<Document> output = ruyuanjilu.aggregate(countPatientId2);
        HistoryOfPastIllness historyOfPastIllness = new HistoryOfPastIllness();
        List<Disease> diseaseList = new ArrayList<>();
        for (Document document : output) {

            Document ruyuanjilu = (Document) document.get("ruyuanjilu");
            String patient_id = document.getString("patient_id");
            String visit_id = document.getString("visit_id");

            if (ruyuanjilu == null) {
                continue;
            }

            //既往史
            Document history_of_past_illness = (Document) ruyuanjilu.get("history_of_past_illness");
            if (Objects.nonNull(history_of_past_illness)) {
                String src = history_of_past_illness.getString("src");
                if (Objects.nonNull(history_of_past_illness.get("disease"))) {
                    continue;
                }
                ArrayList<Document> array = history_of_past_illness.get("disease", ArrayList.class);
                if (Objects.nonNull(array)) {

                    for (Document doc : array) {
                        Disease disease = new Disease();
                        disease.setDisease_name(doc.getString("disease_name"));
                        disease.setDuration_of_illness(doc.getString("duration_of_illness"));
                        disease.setTreatment(doc.getString("treatment"));
                        disease.setDuration_of_illness_unit(doc.getString("duration_of_illness_unit"));
                        diseaseList.add(disease);
                    }

                }
            }
        }
        historyOfPastIllness.setDiseaseList(diseaseList);
        bean.setHistoryOfPastIllness(historyOfPastIllness);
        return bean;
    }


}
