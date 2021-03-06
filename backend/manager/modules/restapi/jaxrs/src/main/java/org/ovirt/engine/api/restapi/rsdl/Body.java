package org.ovirt.engine.api.restapi.rsdl;

import java.util.LinkedList;
import java.util.List;


public class Body {
    private String parameterType;
    private List<Signature> signatures = new LinkedList<Signature>();
    public List<Signature> getSignatures() {
        return signatures;
    }
    public void setSignatures(List<Signature> signatures) {
        this.signatures = signatures;
    }
    public void addSignature(Signature signature) {
        this.signatures.add(signature);
    }
    public void addEmptySignature() {
        this.signatures.add(new Signature());
    }
    public String getParameterType() {
        return parameterType;
    }
    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }
}
