package doanjava1com.example.demo1.Repositories;

import doanjava1com.example.demo1.Models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IInvoiceRepository extends JpaRepository<Invoice, Long> {
}
