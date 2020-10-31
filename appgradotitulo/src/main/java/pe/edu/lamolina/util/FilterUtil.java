package pe.edu.lamolina.util;

import java.util.ArrayList;
import java.util.List;

public class FilterUtil {
	private Integer limit;
    private Integer page;
    private Integer start;
    private Long totalCount;
    private List<FilterProperty> filterPropertyList;

    public FilterUtil() {
        filterPropertyList=new ArrayList<>();
    }

    public List<FilterProperty> getFilterPropertyList() {
        return filterPropertyList;
    }

    public void setFilterPropertyList(List<FilterProperty> filterPropertyList) {
        this.filterPropertyList = filterPropertyList;
    }
    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

}
