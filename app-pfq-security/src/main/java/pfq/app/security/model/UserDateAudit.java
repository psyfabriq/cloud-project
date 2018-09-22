package pfq.app.security.model;

import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@MappedSuperclass
@JsonIgnoreProperties(
        value = {"createdBy", "updatedBy"},
        allowGetters = true
)
public class UserDateAudit extends DateAudit {
	
	    @CreatedBy
	    private Long createdBy;

	    @LastModifiedBy
	    private Long updatedBy;
	    
	    
	    

	    public UserDateAudit() {
			super();
			this.createdBy = new Long(0);
			this.updatedBy = new Long(0);
		}

		public Long getCreatedBy() {
	        return createdBy;
	    }

	    public void setCreatedBy(Long createdBy) {
	        this.createdBy = createdBy;
	    }

	    public Long getUpdatedBy() {
	        return updatedBy;
	    }

	    public void setUpdatedBy(Long updatedBy) {
	        this.updatedBy = updatedBy;
	    }
}
