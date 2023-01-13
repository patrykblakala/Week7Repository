package com.sparta.week6project.DTO;

public class FullEmpInfoDTO {

    private EmployeeDTO employee;

    private DeptEmpDTO department;

    private SalaryDTO salary;

    private TitleDTO title;

    public FullEmpInfoDTO() {
    }

    public FullEmpInfoDTO(EmployeeDTO employeeDTO, DeptEmpDTO deptEmpDTO, SalaryDTO salaryDTO, TitleDTO titleDTO) {
        this.employee = employeeDTO;
        this.department = deptEmpDTO;
        this.salary = salaryDTO;
        this.title = titleDTO;
    }

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employee = employee;
    }

    public DeptEmpDTO getDepartment() {
        return department;
    }

    public void setDepartment(DeptEmpDTO department) {
        this.department = department;
    }

    public SalaryDTO getSalary() {
        return salary;
    }

    public void setSalary(SalaryDTO salary) {
        this.salary = salary;
    }

    public TitleDTO getTitle() {
        return title;
    }

    public void setTitle(TitleDTO title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "FullEmpInfoDTO{" +
                "employeeDTO=" + employee +
                ", deptEmpDTO=" + department +
                ", salaryDTO=" + salary +
                ", titleDTO=" + title +
                '}';
    }
}
