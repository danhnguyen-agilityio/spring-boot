package behavior.chain.responsibility.pattern;

enum MessagePriority {
  Normal, High
}

class Message {
  public String text;
  public MessagePriority priority;

  public Message(String text, MessagePriority priority) {
    this.text = text;
    this.priority = priority;
  }
}

interface IReceiver {
  boolean processMessage(Message msg);
}

class FaxErrorHandler implements IReceiver {
  private IReceiver nextReceiver;

  public FaxErrorHandler(IReceiver nextReceiver) {
    this.nextReceiver = nextReceiver;
  }

  @Override
  public boolean processMessage(Message msg) {
    if (msg.text.contains("Fax")) {
      System.out.println("FaxErrorHandler processed " + msg.priority
        + "priority issue: " + msg.text);
      return true;
    } else {
      if (nextReceiver != null) {
        nextReceiver.processMessage(msg);
      }
    }
    return false;
  }
}

class EmailErrorHandler implements IReceiver {
  private IReceiver nextReceiver;

  public EmailErrorHandler(IReceiver nextReceiver) {
    this.nextReceiver = nextReceiver;
  }

  @Override
  public boolean processMessage(Message msg) {
    if (msg.text.contains("Email")) {
      System.out.println("EmailErrorHandler processed " + msg.priority
          + "priority issue: " + msg.text);
      return true;
    } else {
      if (nextReceiver != null) {
        nextReceiver.processMessage(msg);
      }
    }
    return false;
  }
}

class IssueRaiser {
  public IReceiver setFirstReceiver;

  public IssueRaiser(IReceiver firstReceiver) {
    this.setFirstReceiver = firstReceiver;
  }

  public void raiseMessage(Message msg) {
    if (setFirstReceiver != null) {
      setFirstReceiver.processMessage(msg);
    }
  }
}

public class ChainOfResponsibilityPatternEx {
  public static void main(String[] args) {
    // Making the chain first: IssueRaiser -> FaxErrorHandler -> EmailErrorHandler
    IReceiver faxHandler, emailHandler;
    // end of chain
    emailHandler = new EmailErrorHandler(null);
    // fax handler is before email
    faxHandler = new FaxErrorHandler(emailHandler);

    // starting point: raiser will raise issues and set the first handler
    IssueRaiser raiser = new IssueRaiser(faxHandler);

    Message m1 = new Message("Fax is reaching late to the destination", MessagePriority.Normal);
    Message m2 = new Message("Email is not going", MessagePriority.High);
    Message m3 = new Message("In Email, BBC field is disabled occasionally", MessagePriority.Normal);
    Message m4 = new Message("Fax is not reaching destination", MessagePriority.High);

    raiser.raiseMessage(m1);
    raiser.raiseMessage(m2);
    raiser.raiseMessage(m3);
    raiser.raiseMessage(m4);
  }
}
