package matlabcontrol.link;

/*
 * Copyright (c) 2011, Joshua Kaplan
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the
 * following conditions are met:
 *  - Redistributions of source code must retain the above copyright notice, this list of conditions and the following
 *    disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the
 *    following disclaimer in the documentation and/or other materials provided with the distribution.
 *  - Neither the name of matlabcontrol nor the names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import matlabcontrol.MatlabInvocationException;
import matlabcontrol.MatlabProxy.MatlabThreadProxy;

/**
 *
 * @since 5.0.0
 * @author <a href="mailto:nonother@gmail.com">Joshua Kaplan</a>
 */
public final class MatlabVariable extends MatlabType
{
    private final String _name;

    public MatlabVariable(String name)
    {
        //Validate variable name
        if(name.isEmpty())
        {
            throw new IllegalArgumentException("Invalid MATLAB variable name: " + name);
        }
        char[] nameChars = name.toCharArray();
        if(!Character.isLetter(nameChars[0]))
        {
            throw new IllegalArgumentException("Invalid MATLAB variable name: " + name);
        }
        for(char element : nameChars)
        {
            if(!(Character.isLetter(element) || Character.isDigit(element) || element == '_'))
            {
                throw new IllegalArgumentException("Invalid MATLAB variable name: " + name);
            }
        }
        _name = name;
    }

    String getName()
    {
        return _name;
    }

    @Override
    MatlabTypeSetter getSetter()
    {
        return new MatlabVariableSerializedSetter(_name);
    }

    private static class MatlabVariableSerializedSetter implements MatlabTypeSetter
    {

        private final String _name;

        private MatlabVariableSerializedSetter(String name)
        {
            _name = name;
        }

        @Override
        public void setInMatlab(MatlabThreadProxy proxy, String variableName) throws MatlabInvocationException
        {
            proxy.eval(variableName + " = " + _name + ";");
        }
    }
}