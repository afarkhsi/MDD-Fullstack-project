import { AbstractControl, ValidationErrors } from '@angular/forms';

export function passwordValidator(control: AbstractControl): ValidationErrors | null {
  const value = control.value;
  if (!value) return null;

  const hasMinLength = value.length >= 8;
  const hasLowercase = /[a-z]/.test(value);
  const hasUppercase = /[A-Z]/.test(value);
  const hasDigit = /\d/.test(value);
  const hasSpecialChar = /[^A-Za-z0-9]/.test(value);

  const isValid = hasMinLength && hasLowercase && hasUppercase && hasDigit && hasSpecialChar;

  return isValid ? null : { passwordInvalid: true };
}