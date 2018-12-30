package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PageDto<E> {
    private int pageNumber;
    private int itemsPerPage;
    private int totalPages;
    private List<E> content;
}
