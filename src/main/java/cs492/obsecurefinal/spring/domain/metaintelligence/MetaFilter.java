/*
 * Copyright (C) 2014 Benjamin Arnold
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package cs492.obsecurefinal.spring.domain.metaintelligence;

import cs492.obsecurefinal.spring.domain.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * <filter type="adaptive" value="3">LocationFilter</filter>
 * @author Benjamin Arnold
 */
@Entity
public class MetaFilter extends BaseEntity implements MetaNode {
    
    @Column
    private String name;
	
    @Column
    private String type;
    
    @Column
    private Integer value;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    public Integer getValue() {
	return value;
    }

    public void setValue(Integer value) {
	this.value = value;
    }
    
    
    
}
