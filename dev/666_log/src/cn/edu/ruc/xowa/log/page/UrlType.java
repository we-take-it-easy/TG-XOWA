package cn.edu.ruc.xowa.log.page;

public enum UrlType
{
    // 普通的wiki页面
    NORMAL_PAGE_URL,
    // 搜索结果页面，该页面属于xowa中的一个特殊页面
    SEARCH_LIST_URL,
    // 其他的特殊页面（如history页面等）
    OTHER_SPACIAL_URL,
    // 未知类型的URL
    UNKNOWN_TYPE_URL,
    // 空的url，即不存在该url，例如一个页面没有previous page的时候，它的previous url即为NONE
    NONE
}
