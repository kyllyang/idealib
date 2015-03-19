package org.kyll.common.web.taglib;

import org.apache.taglibs.standard.tag.common.fmt.BundleSupport;
import org.kyll.common.paginated.Paginated;
import org.kyll.common.paginated.PaginatedHelper;
import org.kyll.common.web.util.TagUtil;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class PaginatedTag extends TagSupport {
	private Paginated paginated;
	private String url;
	private String params;
	private String onBeforeHref;

	public Paginated getPaginated() {
		return paginated;
	}

	public void setPaginated(Paginated paginated) {
		this.paginated = paginated;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getOnBeforeHref() {
		return onBeforeHref;
	}

	public void setOnBeforeHref(String onBeforeHref) {
		this.onBeforeHref = onBeforeHref;
	}

	public int doStartTag() throws JspException {
		if (paginated == null) {
			paginated = PaginatedHelper.defaultPaginated();
		}

		if (url.startsWith("formid:")) {
			return this.submit();
		}
		return this.link();
	}


	public int doEndTag() throws JspException {
		this.paginated = null;
		this.url = null;
		this.params = null;
		this.onBeforeHref = null;
		return super.doEndTag();
	}

	private int link() throws JspException {
		url = ((HttpServletRequest) pageContext.getRequest()).getContextPath() + url;
		JspWriter out = pageContext.getOut();
		try {
			out.print("<span style=\"font-size: 12px;\">");
			out.print(TagUtil.getI18NMessage(findAncestorWithClass(this, BundleSupport.class), pageContext, "paginated_total", null) + " " + paginated.getTotalRecord() + " " + TagUtil.getI18NMessage(findAncestorWithClass(this, BundleSupport.class), pageContext, "paginated_record", null) + " ");
			out.print(paginated.getTotalPage() + " " + TagUtil.getI18NMessage(findAncestorWithClass(this, BundleSupport.class), pageContext, "paginated_page", null) + " " + TagUtil.getI18NMessage(findAncestorWithClass(this, BundleSupport.class), pageContext, "paginated_current", null) + " " + paginated.getCurrentPage() + " " + TagUtil.getI18NMessage(findAncestorWithClass(this, BundleSupport.class), pageContext, "paginated_page", null) + " ");
			out.print("<a href=\"" + url + "?paginated.startRecord=" + paginated.getFirstPage() + "&paginated.maxRecord=" + paginated.getMaxRecord() + "&paginated.duePage=" + paginated.getDuePage() + (params == null ? "" : "&" + params) + "\" onclick=\"onClickHref(this);\">|<</a>&nbsp;");
			out.print("<a href=\"" + url + "?paginated.startRecord=" + paginated.getPrevOnePage() + "&paginated.maxRecord=" + paginated.getMaxRecord() + "&paginated.duePage=" + paginated.getDuePage() + (params == null ? "" : "&" + params) + "\" onclick=\"onClickHref(this);\"><</a>&nbsp;");
			out.print("<a href=\"" + url + "?paginated.startRecord=" + paginated.getPrevPage() + "&paginated.maxRecord=" + paginated.getMaxRecord() + "&paginated.duePage=" + paginated.getDuePage() + (params == null ? "" : "&" + params) + "\" onclick=\"onClickHref(this);\"><<</a>&nbsp;");
			for (int i = 0; i < paginated.getDuePage(); i++) {
				int page = paginated.getCurrentPage() + i + 1;
				if ((page <= paginated.getTotalPage())) {
					out.print("<a href=\"" + url + "?paginated.startRecord=" + ((page - 1) * paginated.getMaxRecord()) + "&paginated.maxRecord=" + paginated.getMaxRecord() + "&paginated.duePage=" + paginated.getDuePage() + (params == null ? "" : "&" + params) + "\" onclick=\"onClickHref(this);\">" + page + "</a>&nbsp;");
				}
			}
			out.print("<a href=\"" + url + "?paginated.startRecord=" + paginated.getNextPage() + "&paginated.maxRecord=" + paginated.getMaxRecord() + "&paginated.duePage=" + paginated.getDuePage() + (params == null ? "" : "&" + params) + "\" onclick=\"onClickHref(this);\">>></a>&nbsp;");
			out.print("<a href=\"" + url + "?paginated.startRecord=" + paginated.getNextOnePage() + "&paginated.maxRecord=" + paginated.getMaxRecord() + "&paginated.duePage=" + paginated.getDuePage() + (params == null ? "" : "&" + params) + "\" onclick=\"onClickHref(this);\">></a>&nbsp;");
			out.print("<a href=\"" + url + "?paginated.startRecord=" + paginated.getLastPage() + "&paginated.maxRecord=" + paginated.getMaxRecord() + "&paginated.duePage=" + paginated.getDuePage() + (params == null ? "" : "&" + params) + "\" onclick=\"onClickHref(this);\">>|</a>&nbsp;");
			out.print("<select onchange=\"skipPage(this.options[this.selectedIndex].value);\" style=\"border-style: none; width: 40px;\">");
			for (int i = 0; i < paginated.getTotalPage(); i++) {
				out.print("<option");
				out.print(" value=\"" + (i * paginated.getMaxRecord()) + "\"");
				if (i == (paginated.getCurrentPage() - 1)) {
					out.print(" selected=\"selected\"");
				}
				out.print(">" + (i + 1) + "</option>");
			}
			out.print("</select>");
			out.print("<script type=\"text/javascript\" language=\"JavaScript\">");
			out.print("function skipPage(value) {");
			out.print(onBeforeHref == null ? "" : onBeforeHref + ";");
			out.print("var pv = document.getElementById('paginatedParams').value;");
			out.print("location.href=\"" + url + "?paginated.startRecord=\" + value + \"&paginated.maxRecord=" + paginated.getMaxRecord() + "&paginated.duePage=" + paginated.getDuePage() + "\" + \"" + (params == null ? "" : "&" + params) + "\" + (pv == \"\" ? \"\" : '&' + pv);");
			out.print("}");
			out.print("function onClickHref(a) {");
			out.print(onBeforeHref == null ? "" : onBeforeHref + ";");
			out.print("var pv = document.getElementById('paginatedParams').value;");
			out.print("if (pv != \"\") {");
			out.print("a.href += '&' + pv;");
			out.print("}");
			out.print("}");
			out.print("</script>");
			out.print("</span>");
			out.print("<input type=\"hidden\" id=\"paginatedParams\"/>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}

	private int submit() throws JspException {
		String formid = url.substring(url.indexOf(':') + 1).trim();

		JspWriter out = pageContext.getOut();
		try {
			out.print("<span style=\"font-size: 12px;\">");
			out.print("<input type=\"hidden\" id=\"paginated.startRecord\" name=\"paginated.startRecord\" value=\"" + Paginated.DEFAULT_STARTRECORD + "\"/>");
			out.print("<input type=\"hidden\" id=\"paginated.maxRecord\" name=\"paginated.maxRecord\" value=\"" + Paginated.DEFAULT_MAXRECORD + "\"/>");
			out.print("<input type=\"hidden\" id=\"paginated.duePage\" name=\"paginated.duePage\" value=\"" + Paginated.DEFAULT_DUEPAGE + "\"/>");
			out.print(TagUtil.getI18NMessage(findAncestorWithClass(this, BundleSupport.class), pageContext, "paginated_total", null) + " " + paginated.getTotalRecord() + " " + TagUtil.getI18NMessage(findAncestorWithClass(this, BundleSupport.class), pageContext, "paginated_record", null) + " ");
			out.print(paginated.getTotalPage() + " " + TagUtil.getI18NMessage(findAncestorWithClass(this, BundleSupport.class), pageContext, "paginated_page", null) + " " + TagUtil.getI18NMessage(findAncestorWithClass(this, BundleSupport.class), pageContext, "paginated_current", null) + " " + paginated.getCurrentPage() + " " + TagUtil.getI18NMessage(findAncestorWithClass(this, BundleSupport.class), pageContext, "paginated_page", null) + " ");
			out.print("<a href=\"#\" onclick=\"onClickHref(" + paginated.getFirstPage() + ", " + paginated.getMaxRecord() + ", " + paginated.getDuePage() + ");\">|<</a>&nbsp;");
			out.print("<a href=\"#\" onclick=\"onClickHref(" + paginated.getPrevOnePage() + ", " + paginated.getMaxRecord() + ", " + paginated.getDuePage() + ");\"><</a>&nbsp;");
			out.print("<a href=\"#\" onclick=\"onClickHref(" + paginated.getPrevPage() + ", " + paginated.getMaxRecord() + ", " + paginated.getDuePage() + ");\"><<</a>&nbsp;");
			for (int i = 0; i < paginated.getDuePage(); i++) {
				int page = paginated.getCurrentPage() + i + 1;
				if ((page <= paginated.getTotalPage())) {
					out.print("<a href=\"#\" onclick=\"onClickHref(" + ((page - 1) * paginated.getMaxRecord()) + ", " + paginated.getMaxRecord() + ", " + paginated.getDuePage() + ");\">" + page + "</a>&nbsp;");
				}
			}
			out.print("<a href=\"#\" onclick=\"onClickHref(" + paginated.getNextPage() + ", " + paginated.getMaxRecord() + ", " + paginated.getDuePage() + ");\">>></a> ");
			out.print("<a href=\"#\" onclick=\"onClickHref(" + paginated.getNextOnePage() + ", " + paginated.getMaxRecord() + ", " + paginated.getDuePage() + ");\">></a> ");
			out.print("<a href=\"#\" onclick=\"onClickHref(" + paginated.getLastPage() + ", " + paginated.getMaxRecord() + ", " + paginated.getDuePage() + ");\">>|</a> ");
			out.print("<select onchange=\"skipPage(this.options[this.selectedIndex].value);\" style=\"border-style: none; width: 40px;\">");
			for (int i = 0; i < paginated.getTotalPage(); i++) {
				out.print("<option");
				out.print(" value=\"" + (i * paginated.getMaxRecord()) + "\"");
				if (i == (paginated.getCurrentPage() - 1)) {
					out.print(" selected=\"selected\"");
				}
				out.print(">" + (i + 1) + "</option>");
			}
			out.print("</select>");
			out.print("<script type=\"text/javascript\" language=\"JavaScript\">");
			out.print("function skipPage(value) {");
			out.print(onBeforeHref == null ? "" : onBeforeHref + ";");
			out.print("onClickHref(value, " + paginated.getMaxRecord() + ", " + paginated.getDuePage() + ");");
			out.print("}");
			out.print("function onClickHref(startRecord, maxRecord, duePage) {");
			if (paginated.getTotalRecord() > 0) {
				out.print(onBeforeHref == null ? "" : onBeforeHref + ";");
				out.print("document.getElementById(\"paginated.startRecord\").value = startRecord;");
				out.print("document.getElementById(\"paginated.maxRecord\").value = maxRecord;");
				out.print("document.getElementById(\"paginated.duePage\").value = duePage;");
				out.print("var form = document.getElementById(\"" + formid + "\");");
				out.print("form.submit();");
			}
			out.print("}");
			out.print("</script>");
			out.print("</span>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}
}
