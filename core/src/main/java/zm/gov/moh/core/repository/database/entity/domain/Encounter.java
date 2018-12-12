package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import org.threeten.bp.ZonedDateTime;

@Entity
public class Encounter {

    @PrimaryKey
    public long encounter_id;
    public long encounter_type;
    public long patient_id;
    public long location_id;
    public long form_id;
    public ZonedDateTime encounter_datetime;
    public Long creator;
    public ZonedDateTime date_created;
    public short voided;
    public Long voided_by;
    public ZonedDateTime date_voided;
    public String void_reason;
    public Long changed_by;
    public ZonedDateTime date_changed;
    public long visit_id;
    public String uuid;
}
