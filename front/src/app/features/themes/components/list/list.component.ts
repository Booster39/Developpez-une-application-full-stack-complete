/*import { Component } from '@angular/core';
import { User } from 'src/app/interfaces/user.interface';
import { SessionService } from 'src/app/services/session.service';
import { ThemesService } from '../../services/themes.service';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent {

  public themes$ = this.themesService.all();

  constructor(
    private sessionService: SessionService,
    private themesService: ThemesService
  ) { }

  get user(): User | undefined {
    return this.sessionService.user;
  }
}
*/