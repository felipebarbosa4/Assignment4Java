/***************************************************************************
 * File:  AcademicStudentClub.java Course materials (23S) CST 8277
 * 
 * @author Teddy Yap
 * @author Shariar (Shawn) Emami
 * @date August 28, 2022
 * 
 *  Updated by:  Group 01
 *   040878158, Adam , Jenah (as from ACSIS)
 *   studentId, Felipe, Barbosa (as from ACSIS)
 *   041070895, Chamini Savindya, Demuni (as from ACSIS)
 * 
 */
package acmecollege.entity;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

//TODO ASC01 - Add missing annotations, please see Week 9 slides page 15.  Value 1 is academic and value 0 is non-academic.
@Entity
@DiscriminatorValue(value = "1")
public class AcademicStudentClub extends StudentClub implements Serializable {
	private static final long serialVersionUID = 1L;

	public AcademicStudentClub() {
		super(true);
	}
}
