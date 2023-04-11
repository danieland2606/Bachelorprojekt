package EDA.MeowMed.Policy.Persistence;


import EDA.MeowMed.Policy.Persistence.Entity.ObjectOfInsurance;
import EDA.MeowMed.Policy.Persistence.Entity.Policy;
import EDA.MeowMed.Policy.View.PolicyOverview;
import EDA.MeowMed.Policy.View.PolicyOverviewProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {

    //TODO: Daran muss noch gearbeitet
    //@Query("SELECT p FROM policy p JOIN new p.objectOfInsurance o  WHERE customer_id = ?1 AND p.start_date = ?2 AND p. ")
    //public List<PolicyOverviewProjection> getPolicyList(long customerID, String objectOfInsuranceName, String startDate, String endDate, Integer coverage);

    /**
     Finds a "Policy" in the database based on its ID and the ID of its associated Customer.

     The method executes a JPQL query to load the attributes "id", "coverage", "startDate", "endDate"
     ,"objectOfInsurance", and "premium" of the "Policy" that matches the given criteria.

     WARNING! This method returns the FULL VIEW of the Policy object and is not needed, but can be useful.

     @param customerID The ID of the associated Customer

     @param policyID The ID of the "Policy"

     @return A "Policy" object containing the requested attributes or null if no matching "Policy" was found.
     */
    @Query("SELECT p FROM Policy p WHERE p.customer.id = ?1 AND p.id = ?2")
    public Policy findPolicyByCustomerIDAndPolicyID(long customerID, long policyID);

    /**
     Retrieves the policy overview projection for a given customer ID and policy ID. This method executes a JPQL query to
     fetch the required data from the database.

     @param customerID the ID of the customer associated with the policy

     @param policyID the ID of the policy to retrieve the overview for

     @return the policy overview projection containing the ID, coverage, premium, object of insurance, start date, and end date

     @throws javax.persistence.EntityNotFoundException if the customer or policy is not found in the database
     */
    @Query("SELECT p.id as id, p.coverage as coverage, p.premium as premium, p.objectOfInsurance as objectOfInsurance, p.startDate as startDate, p.endDate as endDate FROM Policy p WHERE p.customer.id = :customerID AND p.id = :policyID")
    public PolicyOverviewProjection findPolicyOverviewByCustomerIDAndPolicyID(long customerID, long policyID);

}


//@Query("SELECT p.id, p.coverage, p.startDate, p.endDate, p.objectOfInsurance, p.premium FROM Policy p WHERE p.customer.id = ?1 AND p.id = ?2")
