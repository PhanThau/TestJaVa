package doanjava1com.example.demo1.Repositories;

import doanjava1com.example.demo1.Models.ItemInvoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IItemInvoiceRepository extends JpaRepository<ItemInvoice, Long> {
}
