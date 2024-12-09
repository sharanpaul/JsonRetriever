
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonToJava {
    public static void main(String[] args) throws RuntimeException {
        String jsonString = "{"
                + "\"organization\": {"
                + "\"name\": \"Tech Solutions Inc.\","
                + "\"location\": {"
                + "\"city\": \"New York\","
                + "\"country\": \"USA\","
                + "\"address\": {"
                + "\"street\": \"123 Innovation Way\","
                + "\"zipcode\": \"10001\""
                + "}"
                + "},"
                + "\"departments\": ["
                + "{"
                + "\"name\": \"Engineering\","
                + "\"employees\": ["
                + "{"
                + "\"id\": 101,"
                + "\"name\": \"Alice Johnson\","
                + "\"designation\": \"Software Engineer\","
                + "\"skills\": [\"Java\", \"Python\", \"SQL\"],"
                + "\"contact\": {"
                + "\"email\": \"alice.johnson@techsolutions.com\","
                + "\"phone\": \"123-456-7890\""
                + "},"
                + "\"projects\": ["
                + "{"
                + "\"name\": \"Project Alpha\","
                + "\"role\": \"Developer\","
                + "\"duration\": \"6 months\","
                + "\"technologies\": [\"Spring Boot\", \"React\"]"
                + "},"
                + "{"
                + "\"name\": \"Project Beta\","
                + "\"role\": \"Lead Developer\","
                + "\"duration\": \"1 year\","
                + "\"technologies\": [\"Node.js\", \"Angular\"]"
                + "}"
                + "]"
                + "},"
                + "{"
                + "\"id\": 102,"
                + "\"name\": \"Bob Smith\","
                + "\"designation\": \"DevOps Engineer\","
                + "\"skills\": [\"Docker\", \"Kubernetes\", \"AWS\"],"
                + "\"contact\": {"
                + "\"email\": \"bob.smith@techsolutions.com\","
                + "\"phone\": \"987-654-3210\""
                + "},"
                + "\"projects\": ["
                + "{"
                + "\"name\": \"Infrastructure Upgrade\","
                + "\"role\": \"DevOps Lead\","
                + "\"duration\": \"8 months\","
                + "\"technologies\": [\"Terraform\", \"Ansible\"]"
                + "}"
                + "]"
                + "}"
                + "]"
                + "},"
                + "{"
                + "\"name\": \"HR\","
                + "\"employees\": ["
                + "{"
                + "\"id\": 201,"
                + "\"name\": \"Clara Williams\","
                + "\"designation\": \"HR Manager\","
                + "\"skills\": [\"Recruitment\", \"Conflict Resolution\"],"
                + "\"contact\": {"
                + "\"email\": \"clara.williams@techsolutions.com\","
                + "\"phone\": \"555-666-7777\""
                + "},"
                + "\"projects\": []"
                + "}"
                + "]"
                + "}"
                + "]"
                + "}"
                + "}";
        try {
            JSONObject jsonObject = JSONObject.parse(jsonString);

            //Extracting the organization name
            String organizationName = extractOrganization(jsonObject);
            System.out.println("Organization Name: " +organizationName);

            //Extracting city from location
            String city = extractCity(jsonObject);
            System.out.println("City: " +city);

            //Extracting department names
            JSONArray departmentNames = extractDepartmentNames(jsonObject);
            System.out.println("Department Names: " +departmentNames);

            //Finding all react projects
            List<String> reactProjects = findAllReactProjects(jsonObject);
            System.out.println("React Projects: "+reactProjects);

            //list all employees who have java
            List<String> javaEmployees = listAllJavaEmployee(jsonObject);
            System.out.println("Employees - Java: "+javaEmployees);

            //Adding new department
            addDepartment(jsonObject);

            //Adding new employee
            String departmentName = "Marketing";
            addEmployee(jsonObject, departmentName);
            System.out.println("Updated JSON");
            System.out.println(jsonObject);
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void addEmployee(JSONObject jsonObject, String departmentName) {
        String retrievedDepartmentName;
        Object organizationElement = jsonObject.get("organization");
        if(organizationElement!=null) {
            JSONObject organizationObject = (JSONObject) organizationElement;
            Object departmentElements = organizationObject.get("departments");
            if (departmentElements != null) {
                JSONArray departmentArray = (JSONArray) departmentElements;
                for (Object department : departmentArray) {
                    JSONObject departmentObject = (JSONObject) department;
                    retrievedDepartmentName = (String) departmentObject.get("name");
                    if(departmentName.equals(retrievedDepartmentName)){
                        Object employeeElement = departmentObject.get("employees");
                        if(employeeElement!=null){
                            JSONArray employeeArray = (JSONArray) employeeElement;
                            JSONObject newEmployee = getJsonObjectForEmployee();
                            employeeArray.add(newEmployee);
                        }
                    }
                }
            }
        }
    }

    private static JSONObject getJsonObjectForEmployee() {
        JSONObject newEmployee = new JSONObject();
        newEmployee.put("id", 103);
        newEmployee.put("name", "John Doe");
        newEmployee.put("designation", "Marketing Analyst");
        JSONArray skills = new JSONArray();
        skills.add("Data");
        skills.add("Analytics");
        newEmployee.put("skills",skills);
        JSONObject contactDetails = new JSONObject();
        contactDetails.put("email", "john.doe@techsolutions.com");
        contactDetails.put("phone", "222-333-4444");
        newEmployee.put("contact", contactDetails);
        return newEmployee;
    }

    private static void addDepartment(JSONObject jsonObject) {
        Object organizationElement = jsonObject.get("organization");
        if (organizationElement != null) {
            JSONObject organizationObject = (JSONObject) organizationElement;
            Object departmentElements = organizationObject.get("departments");
            if (departmentElements != null) {
                JSONArray departmentArray = (JSONArray) departmentElements;
                JSONObject newDepartment = new JSONObject();
                newDepartment.put("name", "Marketing");
                newDepartment.put("employees", new JSONArray());
                departmentArray.add(newDepartment);
            }
        }
    }

    private static List<String> listAllJavaEmployee(JSONObject jsonObject) {
        List<String> javaEmployeesList = new ArrayList<>();
        Object organizationElement = jsonObject.get("organization");
        if(organizationElement!=null) {
            JSONObject organizationObject = (JSONObject) organizationElement;
            Object departmentElements = organizationObject.get("departments");
            if (departmentElements != null) {
                JSONArray departmentArray = (JSONArray) departmentElements;
                for (Object department : departmentArray) {
                    JSONObject departmentObject = (JSONObject) department;
                    Object employeeElement = departmentObject.get("employees");
                    if (employeeElement != null) {
                        JSONArray employeeArray = (JSONArray) employeeElement;
                        for (Object employee : employeeArray) {
                            JSONObject employeeObject = (JSONObject) employee;
                            Object skillElement = employeeObject.get("skills");
                            if(skillElement!=null){
                                JSONArray skillArray = (JSONArray) skillElement;
                                if(skillArray.contains("Java")){
                                    String employeeName = (String) employeeObject.get("name");
                                    javaEmployeesList.add(employeeName);
                                }
                            }
                        }
                    }
                }
            }
        }
        return javaEmployeesList;
    }

    private static List<String> findAllReactProjects(JSONObject jsonObject) {
        List<String> reactProjects = new ArrayList<>();
        Object organizationElement = jsonObject.get("organization");
        if(organizationElement!=null){
            JSONObject organizationObject = (JSONObject) organizationElement;
            Object departmentElements = organizationObject.get("departments");
            if(departmentElements!=null){
                JSONArray departmentArray = (JSONArray) departmentElements;
                for(Object department : departmentArray){
                    JSONObject departmentObject = (JSONObject) department;
                    Object employeeElement = departmentObject.get("employees");
                    if(employeeElement!=null){
                        JSONArray employeeArray = (JSONArray) employeeElement;
                        for(Object employee : employeeArray){
                            JSONObject employeeObject = (JSONObject) employee;
                            Object projectElement = employeeObject.get("projects");
                            if(projectElement!=null){
                                JSONArray projectArray = (JSONArray) projectElement;
                                for(Object project : projectArray){
                                    JSONObject projectObject = (JSONObject) project;
                                    Object technologyElement = projectObject.get("technologies");
                                    if(technologyElement!=null){
                                        JSONArray technologyArray = (JSONArray) technologyElement;
                                        if(technologyArray.contains("React")){
                                                String projectName = (String) projectObject.get("name");
                                                reactProjects.add(projectName);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return reactProjects;
    }

    private static JSONArray extractDepartmentNames(JSONObject jsonObject) {
        JSONArray namesList = new JSONArray();
        Object organizationElement = jsonObject.get("organization");
        if (organizationElement!=null){
            JSONObject organizationObject = (JSONObject) organizationElement;
            Object departmentElements = organizationObject.get("departments");
            if(departmentElements!=null){
                JSONArray departmentNames = (JSONArray) departmentElements;
                for(Object name : departmentNames){
                    JSONObject namesObject = (JSONObject) name;
                    String names = (String) namesObject.get("name");
                    namesList.add(names);
                }
            }
        }
        return namesList;

    }

    private static String extractCity(JSONObject jsonObject) {
        Object organizationElement = jsonObject.get("organization");
        String city = null;
        if (organizationElement != null) {
            JSONObject organizationObject = (JSONObject) organizationElement;
            Object locationElement = organizationObject.get("location");
            if (locationElement != null) {
                JSONObject locationObject = (JSONObject) locationElement;
                city = (String) locationObject.get("city");
            }
        }
        return city;
    }

//    private static JSONArray extractCities(JSONObject jsonObject) {
//        JSONArray cityList = new JSONArray();
//        Object organizationElement = jsonObject.get("organization");
//        if(organizationElement!=null){
//            JSONObject organizationObject = (JSONObject) organizationElement;
//            Object locationElement = organizationObject.get("location");
//            if(locationElement!=null){
//                JSONArray locationArray = (JSONArray) locationElement;
//                for (Object location : locationArray){
//                    JSONObject locationObject = (JSONObject) location;
//                    String city = (String) locationObject.get("city");
//                    cityList.add(city);
//                }
//            }
//        }
//        return cityList;
//    }

    private static String extractOrganization(JSONObject jsonObject) {
        String organizationalName="";
        Object organizationElement = jsonObject.get("organization");
        if (organizationElement != null) {
            JSONObject organizationObject = (JSONObject) organizationElement;
            organizationalName = (String) organizationObject.get("name");
        }
        return organizationalName;
    }
}
