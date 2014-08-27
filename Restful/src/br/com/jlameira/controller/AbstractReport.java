/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jlameira.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;




public abstract class AbstractReport {

	@Resource(name = "jdbc/webservices-ds")
	private DataSource dataSource;

	public JasperPrint fillReport(JasperReport report, Map parameters, JRDataSource dataSource) {

		parameters.put("REPORT_LOCALE", new Locale("pt", "BR"));

		try {
			return JasperFillManager.fillReport(report, parameters, dataSource);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public JasperPrint fillReport(JasperReport report, Map parameters) {

		parameters.put("REPORT_LOCALE", new Locale("pt", "BR"));

		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			return JasperFillManager.fillReport(report, parameters, conn);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException ex) {
//					Logger.getLogger(ReportFacade.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
	}

}
