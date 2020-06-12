package top.vchar.demo.spring.pojo;

import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

/**
 * <p> 火车站点 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/22 23:41
 */
//indexName==db_name, type=table
@Document(indexName = "train", type = "station")
public class TrainStation implements Serializable {

    private String id;
    private String stationName;
    private int hot;
    private int priority;
    private String match;
    private String stationCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public int getHot() {
        return hot;
    }

    public void setHot(int hot) {
        this.hot = hot;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }
}
