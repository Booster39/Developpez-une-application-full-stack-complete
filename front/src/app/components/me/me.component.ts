import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatFormFieldControl } from '@angular/material/form-field';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/features/auth/services/auth.service';
import { UserResponse } from 'src/app/features/topics/interfaces/api/userResponse.interface';
import { User } from 'src/app/interfaces/user.interface';
import { SessionService } from 'src/app/services/session.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss']
})
export class MeComponent implements OnInit {

  public user: User | undefined;
  public meForm: FormGroup |  undefined;
  public id: Number | undefined;

  constructor(private authService: AuthService,
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private matSnackBar: MatSnackBar,
    private userService: UserService,
    private sessionService: SessionService,
    private router: Router,
  ) { }

  public ngOnInit(): void {
    this.authService.me().subscribe(
      (user: User) => this.user = user
    )
    this.initForm();
  }

  public back() {
    window.history.back();
  }

  public submit(): void {
    const formData = new FormData();
    formData.append('name', this.meForm!.get('name')?.value);
    formData.append('email', this.meForm!.get('email')?.value);
    this.id = this.user?.id;
      this.userService
        .update(this.id!.toString(), formData)
        .subscribe((userResponse: UserResponse) =>
          this.exitPage(userResponse)
        );

  }

  private initForm(user?: User): void {
    if (
      user !== undefined
    ) {
      this.router.navigate(['/articles']);
    }
    this.meForm = this.fb.group({
      name: [user ? user.name : '', [Validators.required]],
      email: [user ? user.email : '', [Validators.required, Validators.email]],
    });
  }
  
  private exitPage(userResponse: UserResponse): void {
    this.matSnackBar.open(userResponse.message, 'Close', { duration: 3000 });
    this.router.navigate(['/articles']);
  }

  onLogoutClick() {
    this.sessionService.logOut();

    this.router.navigateByUrl('/');
  }
}