package austen.arts.familymapclient.Model;

public class FamilyResponse {

    Person[] data;

    /**
     * Finds all the family members of a Person
     * and puts them into an array ands saves it for
     * later.
     * @param family
     */
    public FamilyResponse(Person[] family)
    {
        this.data = family;
    }

    public Person[] getData() {
        return data;
    }
}
