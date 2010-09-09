/*
 * Copyright (c) 2010, The Broad Institute
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package org.broadinstitute.sting.utils.exceptions;

import net.sf.samtools.SAMRecord;
import org.broadinstitute.sting.utils.StingException;

import java.io.File;

/**
 * Represents the common user errors detected by Sting / GATK
 *
 * Root class for all GATK user errors, as well as the container for errors themselves
 *
 * User: depristo
 * Date: Sep 3, 2010
 * Time: 2:24:09 PM
 */
public class UserError extends StingException {
    public UserError(String msg) { super(msg); }
    public UserError(String msg, Throwable e) { super(msg, e); }
    private UserError(Throwable e) { super("", e); } // cannot be called, private access                                                                                                                                                                                                                                                                                                                                                                                                                                                                         

    // todo -- fix up exception cause passing
    public static class MissingArgument extends UserError {
        public MissingArgument(String arg, String message) {
            super(String.format("Argument %s was missing: %s", arg, message));
        }
    }

    public static class BadArgumentValue extends UserError {
        public BadArgumentValue(String arg, String message) {
            super(String.format("Argument %s has a bad value: %s", arg, message));
        }
    }

    public static class BadTmpDir extends UserError {
        public BadTmpDir(String message) {
            super(String.format("Failure working with the tmp directory %s. Override with -Djava.io.tmpdir=X on the command line to a bigger/better file system.  Exact error was %s", System.getProperties().get("java.io. tmpdir"), message));
        }
    }

    public static class CouldNotReadInputFile extends UserError {
        public CouldNotReadInputFile(File file, String message) {
            super(String.format("Couldn't read file %s because %s", file.getAbsolutePath(), message));
        }

        public CouldNotReadInputFile(File file, String message, Exception e) {
            super(String.format("Couldn't read file %s because %s with exception %s", file.getAbsolutePath(), message, e.getMessage()));
        }

        public CouldNotReadInputFile(File file, Exception e) {
            this(file, e.getMessage());
        }
    }


    public static class CouldNotCreateOutputFile extends UserError {
        public CouldNotCreateOutputFile(File file, String message, Exception e) {
            super(String.format("Couldn't write file %s because %s with exception %s", file.getAbsolutePath(), message, e.getMessage()));
        }
    }

    public static class MalformedBam extends UserError {
        public MalformedBam(SAMRecord read, String message) {
            super(String.format("SAM/BAM file %s is malformed: %s", read.getFileSource(), message));
        }
    }

    public static class MalformedFile extends UserError {
        public MalformedFile(String message) {
            super(String.format("Unknown file is malformed: %s", message));
        }

        public MalformedFile(String message, Exception e) {
            super(String.format("Unknown file is malformed: %s caused by %s", message, e.getMessage()));
        }

        public MalformedFile(File f, String message, Exception e) {
            super(String.format("File %s is malformed: %s caused by %s", f.getAbsolutePath(), message, e.getMessage()));
        }
    }

    public static class CannotExecuteRScript extends UserError {
        public CannotExecuteRScript(String message, Exception e) {
            super(String.format("Unable to execute RScript command: " + message), e);
        }
    }

}
