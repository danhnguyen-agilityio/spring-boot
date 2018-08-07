package builder.pattern.bank;

class BankAccount {
  private long accountNumber;
  private String owner;
  private String branch;
  private double balance;
  private double interestRate;

  public static class Builder {
    private long accountNumber;
    private String owner;
    private String branch;
    private double balance;
    private double interestRate;

    public Builder(long accountNumber) {
      this.accountNumber = accountNumber;
    }

    public Builder withOwner(String owner) {
      this.owner = owner;
      return this;
    }

    public Builder atBranch(String branch) {
      this.branch = branch;
      return this;
    }

    public Builder openingBalance(double balance) {
      this.balance = balance;
      return this;
    }

    public Builder atRate(double interestRate) {
      this.interestRate = interestRate;
      return this;
    }

    public BankAccount build() {
      BankAccount account = new BankAccount();
      account.accountNumber = this.accountNumber;
      account.owner = this.owner;
      account.branch = this.branch;
      account.balance = this.balance;
      account.interestRate = this.interestRate;
      return account;
    }
  }

  private BankAccount() {}
}

public class BankAccountApp {
  public static void main(String[] args) {
    BankAccount account = new BankAccount.Builder(1234L)
        .withOwner("Marge")
        .atBranch("Spring")
        .openingBalance(100)
        .atRate(2.5)
        .build();

    BankAccount otherAccount = new BankAccount.Builder(4567)
        .atBranch("Autumn")
        .openingBalance(200)
        .atRate(5)
        .build();
  }
}
