/* $Id: InferenceWorkerListener.java 138070 2012-01-10 19:46:08Z sbrown $
 */

package cs492.obsecurefinal.obsecurecyc.opencyc.inference;

//// EXTERNAL IMPORTS
import java.util.EventListener; 
import java.util.List;

/** <P> This interface is one that must be implemented by anyone
 * wishing to listen in on events generated by a InferenceWorker.
 *
 * <p>Copyright 2004 Cycorp, Inc., license is open source GNU LGPL.
 * <p><a href="http://www.opencyc.org/license.txt">the license</a>
 * <p><a href="http://www.opencyc.org">www.opencyc.org</a>
 * <p><a href="http://www.sourceforge.net/projects/opencyc">OpenCyc at SourceForge</a>
 * <p>
 * THIS SOFTWARE AND KNOWLEDGE BASE CONTENT ARE PROVIDED ``AS IS'' AND
 * ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE OPENCYC
 * ORGANIZATION OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE AND KNOWLEDGE
 * BASE CONTENT, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * @see InferenceWorker
 * @author tbrussea, zelal
 * @version $Id: InferenceWorkerListener.java 138070 2012-01-10 19:46:08Z sbrown $
 */
public interface InferenceWorkerListener extends EventListener {
  
  public void notifyInferenceCreated(InferenceWorker inferenceWorker);
  
  public void notifyInferenceStatusChanged(InferenceStatus oldStatus, InferenceStatus newStatus, 
    InferenceWorkerSuspendReason suspendReason, InferenceWorker inferenceWorker);
  
  public void notifyInferenceAnswersAvailable(InferenceWorker inferenceWorker, List newAnswers);

  public void notifyInferenceTerminated(InferenceWorker inferenceWorker, Exception e);

}
