/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.jena.riot.adapters;

import java.io.InputStream ;
import java.io.Reader ;
import java.util.Locale ;

import org.apache.jena.riot.RDFDataMgr ;
import org.apache.jena.riot.RDFLanguages ;

import com.hp.hpl.jena.rdf.model.Model ;
import com.hp.hpl.jena.rdf.model.RDFErrorHandler ;
import com.hp.hpl.jena.rdf.model.RDFReader ;
import com.hp.hpl.jena.rdf.model.impl.RDFDefaultErrorHandler ;
import com.hp.hpl.jena.sparql.util.Context ;
import com.hp.hpl.jena.sparql.util.Symbol ;

/** Adapter from Jena2 original style adapter to RIOT reader. */ 
public class RDFReaderRIOT implements RDFReader
{
    private final String basename ; // This will be per reader instance.
    private final String hintlang ;
    private Context context = new Context() ;
    
    RDFErrorHandler errorHandler = new RDFDefaultErrorHandler();
    
    public RDFReaderRIOT()
    {
        basename = "org.apache.jena.lang.generic" ;
        hintlang = null ;
    }
    
    public RDFReaderRIOT(String lang)
    {
        basename = "org.apache.jena.lang."+lang.toLowerCase(Locale.ENGLISH) ;
        hintlang = lang ;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void read(Model model, Reader r, String base)
    { RDFDataMgr.read(model, r, base, RDFLanguages.nameToLang(hintlang)) ; }

    @Override
    public void read(Model model, InputStream r, String base)
    { RDFDataMgr.read(model, r, base, RDFLanguages.nameToLang(hintlang)) ; }
    
    @Override
    public void read(Model model, String url)
    { RDFDataMgr.read(model, url, RDFLanguages.nameToLang(hintlang)) ; }

    @Override
    public Object setProperty(String propName, Object propValue)
    {
        Symbol sym = Symbol.create(basename+propName) ;
        Object oldObj = context.get(sym) ;
        return oldObj ;
    }
    
    @Override
    public RDFErrorHandler setErrorHandler(RDFErrorHandler errHandler)
    {
        RDFErrorHandler old = errorHandler ;
        errorHandler = errHandler ;
        return old ;
    }
}

