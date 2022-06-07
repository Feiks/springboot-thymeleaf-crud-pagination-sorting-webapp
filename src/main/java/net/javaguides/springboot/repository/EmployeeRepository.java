package net.javaguides.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.javaguides.springboot.model.Employee;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
    @Query
            (value = "Select receiver from greeting where receiver = ?", nativeQuery = true)
    public long yeha(Long id);

    @Query
            (value = "Select code from greeting where code = ?", nativeQuery = true)
    String heyhey(String code);

    @Query
            (value = "Select * from employees where code = ?", nativeQuery = true)
    String name(String code);

    @Transactional
    @Modifying
    @Query
            (value = "Update greeting SET state = 'Money is taken' where receiver = ?", nativeQuery = true)
    void changeValue(Long id);

    @Transactional
    @Modifying
    @Query
            (value = "Update greeting SET state = '?' where id = ?", nativeQuery = true)
    void updateState(String payment, long id);

    @Query
            (value = "Select receiver from greeting where receiver = ?", nativeQuery = true)
    void findReceiverID(long id);
    @Transactional
    @Modifying
    @Query
            (value = "Update employees SET state = 'TAKEN' where first_name=? and last_name=? and code=?", nativeQuery = true)
    void takeMoney(String name, String lastName,String code);

    @Query
            (value = "Select code from employees WHERE code = ?", nativeQuery = true)
    String selectCode(String code);

    @Query
            (value = "SELECT * FROM employees p WHERE p.code LIKE %?%"    , nativeQuery = true)
    public List<Employee> search(String keyword);

    @Query
            (value = "Select SUM(amount) from employees where state = 'TAKEN' and currency='USD'", nativeQuery = true)
    Object findAmountUSD();
    @Query
            (value = "Select  SUM(amount) from employees where state = 'TAKEN' and currency='KGS'", nativeQuery = true)
    Object findAmountKGS();
    @Query
            (value = "Select SUM(amount) from employees where state = 'TAKEN' and currency='EURO'", nativeQuery = true)
    Object findAmountEURO();

    @Query
            (value = "Select SUM(amount) from employees where state = 'In Process'", nativeQuery = true)
    Object findAmountofInprocess();

    @Transactional
    @Modifying
    @Query
            (value = "update employees set who_changed = ? , change_date = ? where id = ?", nativeQuery = true)
    void update(String username, LocalDateTime lt1,long id);

    @Transactional
    @Modifying
    @Query
            (value = "update employees set first_name = ? ,last_name=?,amount = ?, currency=?, code=? , change_date = ?, who_changed = ? where id = ?", nativeQuery = true)
    void update(String firstName, String lastName, String amount, String currency, String code, LocalDateTime lt1 ,String user_change, long id);
}
