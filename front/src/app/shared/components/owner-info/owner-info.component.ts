import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { User } from 'src/app/interfaces/user.interface';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-owner-info',
  templateUrl: './owner-info.component.html',
  styleUrls: ['./owner-info.component.scss']
})
export class OwnerInfoComponent implements OnChanges, OnInit {

  @Input()
  public authorId!: number;

  public name: string | null = null;

  constructor(private userService: UserService) {
  }
  ngOnInit(): void {
    
  }

  public ngOnChanges(changes: SimpleChanges): void {
    //if (changes['authorId'].currentValue !== changes['authorId'].previousValue) {
      this.userService
        .getUserById(changes['authorId'].currentValue)
        .subscribe((user: User) => this.name = user.name);
   // }
  }
}
