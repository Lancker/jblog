package jblog.guohai.org.bll.agent.pingan;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Set;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;



public class NoCheckX509TrustManager implements X509TrustManager, TrustManager {

	private Set alreadyLogged = new HashSet();  
    
    public void checkClientTrusted(X509Certificate[] certs, String arg1) throws CertificateException {  
        throw new CertificateException("NoCheckX509TrustManager can't be used for client certification.");  
    }  
      
    public void checkServerTrusted(X509Certificate[] certs, String alg) throws CertificateException {  
        for (int i=0; i<certs.length; i++) {  
            String name = certs[i].getIssuerDN().getName();  
            if (!alreadyLogged.contains(name)) {  
                System.out.println("Server data (" + alg + ") : " + name+ " NoCheckX509TrustManager");  
                alreadyLogged.add(name);  
            }  
        }  
    }  
      
    public X509Certificate[] getAcceptedIssuers() {  
        return new X509Certificate[0];  
    }  


}