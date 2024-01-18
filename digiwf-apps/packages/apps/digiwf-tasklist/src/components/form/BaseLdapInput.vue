<template>
  <v-autocomplete
    :value="model"
    outlined
    :col="col"
    :no-data-text="noDataText"
    :search-input.sync="search"
    auto-select-first
    :readonly="isReadonly"
    :rules="rules ? rules : true"
    :items="entries"
    :loading="isLoading"
    :filter="filterUsers"
    :label="label"
    :flat="flat"
    :dense="dense"
    :clearable="clearable"
    item-value="lhmObjectId"
    item-text="lhmObjectId"
    placeholder="Benutzer suchen..."
    aria-label="Benutzer suchen"
    @input="input"
  >
    <template #selection="data">
      {{ getFullName(data.item) }}
    </template>
    <template #item="data">
      <template>
        <v-list-item-avatar class="avatar">
          {{ getNamePrefix(data.item) }}
        </v-list-item-avatar>
        <v-list-item-content>
          <v-list-item-title>{{ getFullName(data.item) }}</v-list-item-title>
          <v-list-item-subtitle>{{ castNoAttrAvailable(data.item.email) }}</v-list-item-subtitle>
          <v-list-item-subtitle>
            {{ castNoAttrAvailable(data.item.department) }} <span class="dot">&#8226;</span>
            {{ castNoAttrAvailable(data.item.ou) }}
          </v-list-item-subtitle>
        </v-list-item-content>
      </template>
    </template>
  </v-autocomplete>
</template>

<style scoped>
.avatar {
  background-color: gray;
  color: white;
}

.dot {
  margin: 0 0.2rem;
  font-size: 1.2rem;
  line-height: 1rem;
}

</style>

<script lang="ts">
import {Component, Emit, Prop, Vue, Watch} from "vue-property-decorator";
import {VAutocomplete} from "vuetify/lib";
import {FetchUtils, SearchUserTO, UserRestControllerApiFactory, UserTO} from '@muenchen/digiwf-engine-api-internal';
import {AxiosResponse} from 'axios';
import {ApiConfig} from "../../api/ApiConfig";
import {callGetUserById, callGetUserByUsername, callSearchUser} from "../../api/user/userApiCalls";

@Component({
  components: {
    VAutocomplete
  }
})
export default class BaseLdapInput extends Vue {

  search = "";
  items: UserTO[] = [];
  isLoading = false;
  errorMessage = "";
  model = "";
  lastSearch = "";
  noDataText = "Tippen, um Suche zu starten";

  @Prop()
  value: string | undefined;

  @Prop()
  col: string | undefined;

  @Prop()
  label: string | undefined;

  @Prop()
  ldapOus: string | undefined;

  @Prop()
  readonly: boolean | undefined
  @Prop()
  clearable: boolean | undefined

  @Prop()
  rules: any | undefined

  @Prop()
  flat: boolean | undefined
  @Prop()
  dense: boolean | undefined

  @Emit()
  input(value: any): any {
    return value;
  }

  created(): void {
    if (this.value) {
      this.loadInitialValue(this.value);
    }
  }

  get isReadonly(): boolean {
    return this.readonly || this.isLoading;
  }

  async loadInitialValue(id: string): Promise<void> {
    this.isLoading = true;

    (
      id.match(/^-?\d+$/)
        ? callGetUserById(id)
        : callGetUserByUsername(id)
    )
      .then(user => {
        this.items.push(user);
        this.model = user.lhmObjectId!;
        this.input(user.lhmObjectId);
        this.errorMessage = "";
      })
      .catch(() => {
        this.errorMessage = "Der Benutzer konnte nicht geladen werden.";
        this.model = "";
        this.input("");
      })
      .finally(() => {
        this.isLoading = false;
      });
  }

  getFullName(user: UserTO): string {
    return user.forename + " " + user.surname;
  }

  filterUsers(item: UserTO, queryText: string): boolean {
    const fullName = this.getFullName(item);
    return fullName.toLowerCase().includes(queryText.toLowerCase());
  }

  getNamePrefix(user: UserTO): string {
    return user.forename!.slice(0, 1) + user.surname!.slice(0, 1);
  }

  castNoAttrAvailable(val: string): string {
    if (val === "No_Attribute_Available") {
      return "-";
    }
    return val;
  }

  get entries(): UserTO [] {
    return this.items;
  }

  @Watch("search")
  async queryResults(): Promise<void> {
    if (!this.search || this.search.length < 3) return;

    if (this.lastSearch === this.search.slice(0, 3)) return;

    this.lastSearch = this.search.slice(0, 3);

    this.noDataText = "Benutzer werden gesucht...";
    this.isLoading = true;
    callSearchUser(this.lastSearch, this.ldapOus)
      .then(searchResult => {
        if (this.lastSearch === this.search.slice(0, 3)) {
          this.items = searchResult;
        }
        this.errorMessage = "";
      })
      .catch(() => {
        this.errorMessage = "Der Benutzer konnte nicht geladen werden.";
      })
      .finally(() => {
        this.isLoading = false;
        this.noDataText = "Tippen, um Suche zu starten";
      });
  }

}
</script>

